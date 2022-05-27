
#include <iostream>
#include "../include/connectionHandler.h"
#include <ctime>

using boost::asio::ip::tcp;

using std::cin;
using std::cout;
using std::cerr;
using std::endl;
using std::string;
 
ConnectionHandler::ConnectionHandler(string host, short port): host_(host), port_(port), io_service_(), socket_(io_service_){}
    
ConnectionHandler::~ConnectionHandler() {
    close();
}
 
bool ConnectionHandler::connect() {
    std::cout << "Starting connect to " 
        << host_ << ":" << port_ << std::endl;
    try {
		tcp::endpoint endpoint(boost::asio::ip::address::from_string(host_), port_); // the server endpoint
		boost::system::error_code error;
		socket_.connect(endpoint, error);
		if (error)
			throw boost::system::system_error(error);
    }
    catch (std::exception& e) {
        std::cerr << "Connection failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}
 
bool ConnectionHandler::getBytes(char bytes[], unsigned int bytesToRead) {
    size_t tmp = 0;
	boost::system::error_code error;
    try {
        while (!error && bytesToRead > tmp ) {
			tmp += socket_.read_some(boost::asio::buffer(bytes+tmp, bytesToRead-tmp), error);			
        }
		if(error)
			throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

bool ConnectionHandler::sendBytes(const char bytes[], int bytesToWrite) {
    int tmp = 0;
	boost::system::error_code error;
    try {
        while (!error && bytesToWrite > tmp ) {
			tmp += socket_.write_some(boost::asio::buffer(bytes + tmp, bytesToWrite - tmp), error);
        }
		if(error)
			throw boost::system::system_error(error);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}
 
bool ConnectionHandler::getLine(std::string& line) {
    return getFrameAscii(line, ';');
}

bool ConnectionHandler::sendLine(std::string& line) {
    if(line == "die"){
        return false;
    }
    return sendFrameAscii(line, ';');
}
 
bool ConnectionHandler::getFrameAscii(std::string& frame, char delimiter) {
    char ch;

    // Stop when we encounter the null character. 
    // Notice that the null character is not appended to the frame string.
    try {
		do{
			getBytes(&ch, 1);
            frame.append(1, ch);
        }while (delimiter != ch);
    } catch (std::exception& e) {
        std::cerr << "recv failed (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    char* opC = new char[2];
    opC[0] = frame[0];
    opC[1] = frame[1];
    short op = bytesToShort(opC);
    std::string replace = "";
    if(op == 9){
        replace.append("NOTIFICATION ");
        if(frame[2] == 0)
            replace.append("PM ");
        else
            replace.append("Public ");
        for (int i = 3; i < (int)frame.size() - 1; ++i) {
            if(frame[i] == 0)
                replace.push_back(' ');
            else
                replace.push_back(frame[i]);
        }
    }
    else if(op == 10){
        replace.append("ACK ");
        char* sentopC = new char[2];
        sentopC[0] = frame[2];
        sentopC[1] = frame[3];
        short sentop = bytesToShort(sentopC);
        replace.append(std::to_string(sentop));
        if(sentop == 8 || sentop == 7){
            replace.push_back(' ');
            for (int i = 4; i < (int) frame.size(); i = i + 2) {
                if(frame[i] != ';') {
                    char *param = new char[2];
                    param[0] = frame[i];
                    param[1] = frame[i + 1];
                    short p = bytesToShort(param);
                    replace.append(std::to_string(p));
                    replace.push_back(' ');
                }
                else{
                    replace.push_back(frame[i]);
                }
            }
        } else{
            if (sentop==4){
                replace.push_back(' ');
            }
            for (int i = 4; i < (int) frame.size(); ++i) {
                if(frame[i] != 0) {
                    replace.push_back(frame[i]);
                }
            }
        }
    } else if (op == 11){
            replace.append("ERROR ");
            char* sentopC = new char[2];
            sentopC[0] = frame[2];
            sentopC[1] = frame[3];
            short sentop = bytesToShort(sentopC);
            replace.append(std::to_string(sentop));
            replace.push_back(frame[4]);
    }
    frame = replace;
    return true;
}
 
bool ConnectionHandler::sendFrameAscii(const std::string& frame, char delimiter) {
    std :: string firstword = frame.substr(0, frame.find_first_of(" "));
    std :: string message = frame.substr( frame.find_first_of(" ") + 1);
    short opCode = 0;
    int byteSize = 0;
    char* bytesArray = new char[1<<10];
    if(firstword == "REGISTER"){
        opCode = 1;
        for (int i = 0; i < (int)message.size(); ++i) {
            if(message[i] == ' ')
                bytesArray[i] = 0;
            else
                bytesArray[i] = message[i];
            byteSize++;
        }
        bytesArray[message.length()] = 0;
        byteSize++;
    }
    else if(firstword == "LOGIN"){
        opCode = 2;
        for (int i = 0; i < (int)message.size() - 1; ++i) {
            if(message[i] == ' ')
                bytesArray[i] = 0;
            else
                bytesArray[i] = message[i];
            byteSize++;
        }
        if (message[message.length() - 1] == '1')
            bytesArray[message.length()- 1] = 1;
        else
            bytesArray[message.length()- 1] = 0;
        byteSize++;
    }
    else if(firstword == "LOGOUT"){
        opCode = 3;
    }
    else if(firstword == "FOLLOW"){
        opCode = 4;
        if(message[0] == '0')
            bytesArray[0] = 0;
        else
            bytesArray[0] = 1;
        byteSize++;
        for (int i = 2; i < (int)message.size(); ++i) {
            bytesArray[i-1] = message[i];
            byteSize++;
        }
    }
    else if(firstword == "POST"){
        opCode = 5;
        for (int i = 0; i < (int)message.size(); ++i) {
            bytesArray[i] = message[i];
            byteSize++;
        }
        bytesArray[message.length()] = 0;
        byteSize++;
    }
    else if(firstword == "PM"){
        opCode = 6;
        bool space = false;
        for (int i = byteSize; i < (int)message.size(); ++i) {
            if(message[byteSize] == ' ' && !space) {
                bytesArray[byteSize] = 0;
                space = true;
            }
            else
                bytesArray[byteSize] = message[byteSize];
            byteSize++;
        }
        bytesArray[byteSize] = 0;
        byteSize++;
        time_t now = time(0);
        std :: string date = ctime(&now);
        for (int i = 0; i < (int)date.size(); ++i) {
            bytesArray[byteSize] = date[i];
            byteSize++;
        }
        bytesArray[byteSize] = 0;
        byteSize++;
    }
    else if(firstword == "LOGSTAT"){
        opCode = 7;
    }
    else if(firstword == "STAT"){
        opCode = 8;
        for (int i = 0; i < (int)message.size(); ++i) {
                bytesArray[i] = message[i];
            byteSize++;
        }
        bytesArray[message.length()] = 0;
        byteSize++;
    }
    else if(firstword == "BLOCK"){
        opCode = 12;
        for (int i = 0; i < (int)message.size(); ++i) {
            bytesArray[i] = message[i];
            byteSize++;
        }
        bytesArray[message.length()] = 0;
        byteSize++;
    }
    char* opArray = new char[2];
    shortToBytes(opCode, opArray);
    sendBytes(opArray ,2);
	bool result=sendBytes(bytesArray,byteSize);
	if(!result) return false;
	return sendBytes(&delimiter,1);
}
 
// Close down the connection properly.
void ConnectionHandler::close() {
    try{
        socket_.close();
    } catch (...) {
        std::cout << "closing failed: connection already closed" << std::endl;
    }
}
void ConnectionHandler :: shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}
short ConnectionHandler :: bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}
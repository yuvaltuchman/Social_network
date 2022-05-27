//
// Created by EyalT on 30/12/2021.
//
#include "../include/connectionHandler.h"
#include "../include/readFromServer.h"
#include <cstdlib>
readFromServer ::  readFromServer(ConnectionHandler& ch,bool &disconnect, bool &wait) : connectionHandler(ch), disconnect(disconnect), waitforres(wait){};
void readFromServer :: read(){

    // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
    // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
    while (1) {
        std::string answer;
        if (!connectionHandler.getLine(answer)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

        int len = answer.length();
        // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
        // we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
        answer.resize(len - 1);
        std::cout << answer << std::endl;
        if (answer == "ACK 3") {
            disconnect = true;
            waitforres = true;
            std::cout << "Exiting...\n" << std::endl;
            break;
        } else if(answer == "ERROR 3"){
            waitforres = true;
        } else{
            waitforres = false;
        }
    }
}
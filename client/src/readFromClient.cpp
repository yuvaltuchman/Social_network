//
// Created by EyalT on 30/12/2021.
//
#include "../include/connectionHandler.h"
#include "../include/readFromClient.h"
#include <cstdlib>
readFromClient ::  readFromClient(ConnectionHandler& ch, bool& dis, bool& wait) : connectionHandler(ch), disconnect(dis), waitforres(wait){};
void readFromClient ::write() {
    std::string answer;
    // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
    // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end

    //From here we will see the rest of the ehco client implementation:
    while (1) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        int len = line.length();
        if (!connectionHandler.sendLine(line)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
        std::cout << "Sent " << len + 1 << " bytes to server" << std::endl;
        if(line == "LOGOUT") {
            while (!waitforres) {
            }
            if (disconnect) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
        }
    }
}
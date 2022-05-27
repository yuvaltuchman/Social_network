//
// Created by EyalT on 30/12/2021.
//

#ifndef CLIENTS_SPL3_READFROMSERVER_H
#define CLIENTS_SPL3_READFROMSERVER_H
#include "connectionHandler.h"

class readFromServer {
private:
    ConnectionHandler& connectionHandler;
    bool& disconnect;
    bool& waitforres;
public:
    readFromServer(ConnectionHandler& ch, bool &disconnect, bool &wait);
    void read();
};
#endif //CLIENTS_SPL3_READFROMSERVER_H

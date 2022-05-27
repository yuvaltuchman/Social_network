//
// Created by EyalT on 30/12/2021.
//

#ifndef CLIENTS_SPL3_READFROMCLIENT_H
#define CLIENTS_SPL3_READFROMCLIENT_H
#include "connectionHandler.h"

class readFromClient{
private:
    ConnectionHandler& connectionHandler;
    bool& disconnect;
    bool& waitforres;



public:
    readFromClient(ConnectionHandler& ch, bool &disconnect, bool &wait);
    void write();
};
#endif //CLIENTS_SPL3_READFROMCLIENT_H

#include <cstdlib>
#include "../include/connectionHandler.h"
#include "../include/readFromServer.h"
#include "../include/readFromClient.h"
#include <boost/thread/thread.hpp>

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    bool wait = false;
    bool disconnect = false;
    readFromServer Sreader(connectionHandler, disconnect, wait);
    readFromClient Creader(connectionHandler,disconnect, wait);
    boost :: thread reader (&readFromServer :: read,&Sreader);
    boost :: thread writer (&readFromClient :: write,&Creader);
    reader.join();
    writer.join();
    return 0;
}

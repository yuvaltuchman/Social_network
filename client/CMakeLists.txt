cmake_minimum_required(VERSION 3.16)
project(Clients_SPL3)

set(CMAKE_CXX_STANDARD 14)

find_package(Boost REQUIRED regex date_time system filesystem thread graph)
include_directories(${BOOST_INCLUDE_DIRS})
message("boost lib: ${Boost_LIBRARIES}")
message("boost inc:${Boost_INCLUDE_DIR}")


add_executable(Clients_SPL3 include/connectionHandler.h src/connectionHandler.cpp src/BGSclient.cpp include/readFromServer.h src/readFromServer.cpp include/readFromServer.h include/readFromClient.h src/readFromClient.cpp)

target_link_libraries(Clients_SPL3 ${Boost_LIBRARIES})

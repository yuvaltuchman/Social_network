# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.16

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/yuval/Clients_SPL3

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/yuval/Clients_SPL3/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Clients_SPL3.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Clients_SPL3.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Clients_SPL3.dir/flags.make

CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.o: CMakeFiles/Clients_SPL3.dir/flags.make
CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.o: ../src/connectionHandler.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/yuval/Clients_SPL3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.o -c /home/yuval/Clients_SPL3/src/connectionHandler.cpp

CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/yuval/Clients_SPL3/src/connectionHandler.cpp > CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.i

CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/yuval/Clients_SPL3/src/connectionHandler.cpp -o CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.s

CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.o: CMakeFiles/Clients_SPL3.dir/flags.make
CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.o: ../src/BGSclient.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/yuval/Clients_SPL3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.o -c /home/yuval/Clients_SPL3/src/BGSclient.cpp

CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/yuval/Clients_SPL3/src/BGSclient.cpp > CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.i

CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/yuval/Clients_SPL3/src/BGSclient.cpp -o CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.s

CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.o: CMakeFiles/Clients_SPL3.dir/flags.make
CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.o: ../src/readFromServer.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/yuval/Clients_SPL3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.o -c /home/yuval/Clients_SPL3/src/readFromServer.cpp

CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/yuval/Clients_SPL3/src/readFromServer.cpp > CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.i

CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/yuval/Clients_SPL3/src/readFromServer.cpp -o CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.s

CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.o: CMakeFiles/Clients_SPL3.dir/flags.make
CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.o: ../src/readFromClient.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/yuval/Clients_SPL3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.o"
	/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.o -c /home/yuval/Clients_SPL3/src/readFromClient.cpp

CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.i"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /home/yuval/Clients_SPL3/src/readFromClient.cpp > CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.i

CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.s"
	/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /home/yuval/Clients_SPL3/src/readFromClient.cpp -o CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.s

# Object files for target Clients_SPL3
Clients_SPL3_OBJECTS = \
"CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.o" \
"CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.o" \
"CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.o" \
"CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.o"

# External object files for target Clients_SPL3
Clients_SPL3_EXTERNAL_OBJECTS =

Clients_SPL3: CMakeFiles/Clients_SPL3.dir/src/connectionHandler.cpp.o
Clients_SPL3: CMakeFiles/Clients_SPL3.dir/src/BGSclient.cpp.o
Clients_SPL3: CMakeFiles/Clients_SPL3.dir/src/readFromServer.cpp.o
Clients_SPL3: CMakeFiles/Clients_SPL3.dir/src/readFromClient.cpp.o
Clients_SPL3: CMakeFiles/Clients_SPL3.dir/build.make
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_regex.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_date_time.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_system.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_filesystem.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_thread.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_graph.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_atomic.so.1.71.0
Clients_SPL3: /usr/lib/x86_64-linux-gnu/libboost_regex.so.1.71.0
Clients_SPL3: CMakeFiles/Clients_SPL3.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/yuval/Clients_SPL3/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Linking CXX executable Clients_SPL3"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Clients_SPL3.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Clients_SPL3.dir/build: Clients_SPL3

.PHONY : CMakeFiles/Clients_SPL3.dir/build

CMakeFiles/Clients_SPL3.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Clients_SPL3.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Clients_SPL3.dir/clean

CMakeFiles/Clients_SPL3.dir/depend:
	cd /home/yuval/Clients_SPL3/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/yuval/Clients_SPL3 /home/yuval/Clients_SPL3 /home/yuval/Clients_SPL3/cmake-build-debug /home/yuval/Clients_SPL3/cmake-build-debug /home/yuval/Clients_SPL3/cmake-build-debug/CMakeFiles/Clients_SPL3.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Clients_SPL3.dir/depend


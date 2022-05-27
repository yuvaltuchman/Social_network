
(1)
(1.1)Servers:
	maven build command: mvn package
	Reactor command line: mvn exec:java -Dexec.mainClass="bgu.spl.net.srv.Reactor" -Dexec.args="<port> <numofthreads>"
	TPC Servercommand line: mvn exec:java -Dexec.mainClass="bgu.spl.net.srv.BguServer" -Dexec.args="<port>"
	
Clients:
	client build command: make
	client command line: ./bin/BGSclient <IP> <port>
	



(1.2) EXAMPLES:
	REGISTER RICK pain 21-10-1997
	LOGIN RICK pain 1
	FOLLOW 0 RICK
	LOGOUT
	POST @RICK wahad manyak
	PM RICK manyak
	LOGSTAT
	BLOCK RICK
	STAT RICK
	
(2)list of filtered words is saved in the BGUconnections Class as a String array named "filters"

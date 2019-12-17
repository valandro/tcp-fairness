# Global variables
CLIENT_PATH = client/src/
SERVER_PATH = server/src/
DEFAULT_PORT = 12345
DEFAULT_HOST = localhost

clean:
	rm -f ./metrics.csv  $(CLIENT_PATH)Client.class $(SERVER_PATH)Server.class $(SERVER_PATH)ReceiveDataThread.class
build:
	javac $(CLIENT_PATH)Client.java
	javac $(SERVER_PATH)Server.java
graph:
	python plot.py
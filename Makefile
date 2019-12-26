# Global variables
CLIENT_PATH = client/src/
SERVER_PATH = server/src/
DEFAULT_PORT = 12345
DEFAULT_HOST = localhost
HELP_TEXT = [make clean] - Remove all files\n[make build] - Generate all .class files
CHANGE_DIR = cd $(SERVER_PATH) &&

help:
	echo "$(HELP_TEXT)"
clean:
	rm -f ./metrics.csv  $(CLIENT_PATH)Client.class $(SERVER_PATH)Server.class $(SERVER_PATH)ReceiveDataThread.class
build:
	javac $(CLIENT_PATH)Client.java
	$(CHANGE_DIR) javac ReceiveDataThread.java
	$(CHANGE_DIR) javac Server.java
graph:
	python plot.py
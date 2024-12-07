main = fr._42.chat.Program

.PHONY: build run re clean

build: 
	mvn install

run: build
	mvn exec:java -Dexec.mainClass=$(main)

re: clean build

clean:
	mvn clean
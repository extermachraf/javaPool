main = target/chat-app-1.0-SNAPSHOT.jar

.PHONY: build run re clean

build: 
	mvn install

run:
	java -jar $(main)

re: clean build

clean:
	mvn clean
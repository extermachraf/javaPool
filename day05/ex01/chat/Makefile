main = target/chat-1.0-SNAPSHOT-jar-with-dependencies.jar

.PHONY: build run re clean

# Build the project
build: 
	mvn install

# Run the jar file
run: 
	java -jar $(main)

# Rebuild the project (clean and then build)
re: clean build

# Clean the project
clean:
	mvn clean

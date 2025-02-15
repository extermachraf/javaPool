main = target/tests-1.0-SNAPSHOT.jar

.PHONY: build run re clean

# Build the project
build: 
	mvn install

#run tests
test:
	mvn test

# Run the jar file
run: 
	java -jar $(main)

# Rebuild the project (clean and then build)
re: clean build

# Clean the project
clean:
	mvn clean

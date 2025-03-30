# Makefile for Maven-based Java project in ex01/serverfolder

# Variables
MVN = mvn
JAR_NAME = target/Chat-folder-1.0-SNAPSHOT.jar  # Adjust if your JAR name is different

# Default target
all: package

# Package the project using Maven
package:
	$(MVN) clean package

# Run the JAR file
run: package
	java -jar $(JAR_NAME)

# Clean the project using Maven
clean:
	$(MVN) clean

.PHONY: all package run clean
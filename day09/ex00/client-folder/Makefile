# Makefile for Java Client

# Maven command
MVN = mvn

# Project directories
SRC_DIR = src/main/java
BUILD_DIR = target
PACKAGE = fr._42

# Target JAR file
JAR_FILE = $(BUILD_DIR)/client-folder-1.0-SNAPSHOT-jar-with-dependencies.jar

# Default target
all: clean build

# Clean build artifacts
clean:
	$(MVN) clean

# Build the project
build:
	$(MVN) package

# Run the client using the executable jar
run: build
	java -jar $(JAR_FILE)

# Install dependencies
deps:
	$(MVN) dependency:resolve

# Show help
help:
	@echo "Available targets:"
	@echo "  all                - Clean and build the project"
	@echo "  clean              - Remove build artifacts"
	@echo "  build              - Build the project"
	@echo "  run                - Run the client using the executable jar"
	@echo "  deps               - Install dependencies"
	@echo "  help               - Show this help message"

.PHONY: all clean build run deps help 
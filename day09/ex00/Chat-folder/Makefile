# Makefile for Java Server

# Maven command
MVN = mvn

# Project directories
SRC_DIR = src/main/java
BUILD_DIR = target
PACKAGE = fr._42

# Target JAR file
JAR_FILE = $(BUILD_DIR)/Chat-folder-1.0-SNAPSHOT.jar

# Default target
all: clean build

# Clean build artifacts
clean:
	$(MVN) clean

# Build the project
build:
	$(MVN) package

# Run the server with all dependencies
run: build
	$(MVN) exec:java -Dexec.mainClass="$(PACKAGE).Main"

# Install dependencies
deps:
	$(MVN) dependency:resolve

# Create a classpath with all dependencies
classpath:
	$(MVN) dependency:build-classpath -Dmdep.outputFile=classpath.txt

# Package with dependencies (creates an uber jar with all dependencies)
package-with-deps:
	$(MVN) clean compile assembly:single

# Show help
help:
	@echo "Available targets:"
	@echo "  all                - Clean and build the project"
	@echo "  clean              - Remove build artifacts"
	@echo "  build              - Build the project"
	@echo "  run                - Run the server with all dependencies"
	@echo "  deps               - Install dependencies"
	@echo "  help               - Show this help message"

.PHONY: all clean build run deps classpath package-with-deps help 
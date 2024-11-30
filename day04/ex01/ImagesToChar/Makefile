JAVA_FILES = src/java
RESOURCES = src/resources
TARGET_DIR = target
JAR_NAME = $(TARGET_DIR)/ImagesToChar.jar
MANIFEST = src/manifest.txt

# Default target
all: Compile jar run

Compile:
	@javac -d $(TARGET_DIR) $(JAVA_FILES)/*.java

jar:
	@jar cfm $(JAR_NAME) $(MANIFEST) -C $(TARGET_DIR) . -C $(RESOURCES) .

run:
	@java -jar $(JAR_NAME) $(args)

list:
	@jar tf $(JAR_NAME)

clean:
	@rm -rf $(TARGET_DIR)/

.PHONY: all Compile jar run clean

# Variables
SRC_DIR = src/java
TARGET_DIR = target
LIB_DIR = lib
RESOURCE_DIR = src/resources
JAR_NAME = ImagesToChar.jar
MAIN_CLASS = ex02.ImagesToChar.src.java.Program
MANIFEST = src/manifest.txt
JARS = $(LIB_DIR)/jcommander-1.81.jar:$(LIB_DIR)/JColor-5.5.1.jar

# Compile the Java files
compile:
	mkdir -p $(TARGET_DIR)
	javac -cp "$(JARS)" -d $(TARGET_DIR) $(SRC_DIR)/*.java

# Copy resources to target directory
copy-resources:
	mkdir -p $(TARGET_DIR)/resources
	cp -r $(RESOURCE_DIR)/* $(TARGET_DIR)/resources

# Create the JAR file
jar: compile copy-resources
	jar cfm $(JAR_NAME) $(MANIFEST) -C $(TARGET_DIR) .

# Clean up the target directory
clean:
	rm -rf $(TARGET_DIR)
	rm -f $(JAR_NAME)

# Run the program with arguments
run: jar
	echo "Running the program..."
	@java -jar $(JAR_NAME) --white . --black o

# Default target when `make` is run without arguments
all: clean jar

# Set the directories for source and target
SRC_DIR = src
TARGET_DIR = target
BIN_DIR = $(TARGET_DIR)/ex00/src

# Java source files
SRC_FILES = $(SRC_DIR)/app/Program.java $(SRC_DIR)/logic/BmpPrint.java $(SRC_DIR)/logic/CustomException.java

# Define the classpath
CLASSPATH = $(TARGET_DIR)

# Compiler and JVM
JAVAC = javac
JAVA = java

# The default target (make)
all: compile run

# Compile the Java files
compile:
	@mkdir -p $(BIN_DIR)  # Ensure the target directory exists
	@$(JAVAC) -d $(TARGET_DIR) $(SRC_FILES) 

# Run the Program
run:
	@$(JAVA) -cp $(TARGET_DIR) ex00.src.app.Program $(ARGS)

# Clean the target directory (remove compiled files but keep the target structure)
clean:
	@rm -rf $(TARGET_DIR)/ex00/src/*.class

# Remove all compiled files and target directory
fclean: clean
	@rm -rf $(TARGET_DIR)

# Rebuild the project (clean and then compile/run)
re: fclean all

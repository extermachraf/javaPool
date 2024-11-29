# Image Converter Program

## Description
This program converts an image to a textual representation using specified characters for black and white pixels.

## Requirements
- Java Development Kit (JDK)
- Make

## Usage
**Arguments:**
1. `--black=<character>`: The character to use for black pixels.
2. `--white=<character>`: The character to use for white pixels.
3. `--image-path=<absolute path>`: The absolute path to the image file.

**Example:**
```sh
java -cp target ex00.src.app.Program --black=# --white=. --image-path=/absolute/path/to/image.bmp
```

## Makefile Commands
- `make` or `make all`: Compiles and runs the program.
- `make compile`: Compiles the Java files.
- `make run`: Runs the compiled program.
- `make clean`: Cleans up the compiled files but keeps the target structure.
- `make fclean`: Cleans up all compiled files and the target directory.
- `make re`: Rebuilds the project (cleans and then compiles/runs).

## Compilation and Running
**Compile:**
To compile the Java files, use the following command:
```sh
make compile
```

**Run:**
To run the compiled program, use the following command:
```sh
make run ARGS="--black=# --white=. --image-path=/absolute/path/to/image.bmp"
```

**Clean:**
To clean up the compiled files but keep the target structure, use:
```sh
make clean
```

**Full Clean:**
To remove all compiled files and the target directory, use:
```sh
make fclean
```

**Rebuild:**
To clean and rebuild the project, use:
```sh
make re
```

## Program Structure
**Main Class:**
- `Program.java`: This is the entry point of the program. It parses the input arguments and initializes the `BmpPrint` class to display the image.

**Helper Classes:**
- `BmpPrint.java`: Contains the logic to convert the image to a string representation.
- `CustomException.java`: Custom exception class for handling errors.

## Error Handling
The program includes custom error handling for invalid arguments and missing files. Errors are displayed in red in the console.
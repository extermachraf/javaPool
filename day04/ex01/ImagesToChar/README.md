# ImagesToChar

## Description
`ImagesToChar` is a Java program that converts BMP images to a string representation using specified characters for black and white pixels.

## Requirements
- Java Development Kit (JDK) 8 or higher

## Project Structure
```
ImagesToChar/
├── src/
│   ├── java/
│   │   └── ex01/ImagesToChar/src/java/*java
│   ├── resources/
│   └── manifest.txt
├── target/
└── Makefile
```

## Compilation and Execution

### Using Makefile

1. **Compile the program:**
    ```sh
    make Compile
    ```

2. **Create the JAR file:**
    ```sh
    make jar
    ```

3. **Run the program:**
    ```sh
    make run args="--black=# --white=."
    ```

4. **List contents of the JAR file:**
    ```sh
    make list
    ```

5. **Clean the target directory:**
    ```sh
    make clean
    ```

## Arguments
- `--black=<character>`: Character to represent black pixels.
- `--white=<character>`: Character to represent white pixels.

Example:
```sh
java -jar target/ImagesToChar.jar --black=# --white=.
```

## Error Handling
The program will display error messages in red if:
- The arguments are not in the correct format.
- The arguments are not single characters.
- There is an issue reading the BMP file.

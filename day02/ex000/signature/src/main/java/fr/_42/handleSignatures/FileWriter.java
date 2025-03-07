package fr._42.ReadSignatures;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class FileWriter {
    private final File resultFile;

    public FileWriter(String filePath) {
        this.resultFile = new File(filePath);
    }


    public void writeToFile(String content) {
        try (PrintWriter writer = new PrintWriter(resultFile)) {
            // Clear existing content before writing
            writer.print("");
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
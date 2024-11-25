package day02.ex01;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        try {
            List<String> wordOfFile1 = ReadFile.read("/home/achraf/42-projects/javaPool/day02/ex01/files/file1.txt")
                    .TransformContentToList();
        } catch (IOException ex) {
            System.err.println("\u001B[31m" + "an error occurred when trying to read content from the file\n"
                    + ex.getMessage() + "\u001B[0m");
        }
    }
}

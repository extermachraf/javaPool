package day02.ex01;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        try {
            List<String> wordOfFile1 = ReadFile.read("/home/achraf/42-projects/javaPool/day02/ex01/files/file1.txt")
                    .TransformContentToList();
            List<String> wordOfFile2 = ReadFile.read("/home/achraf/42-projects/javaPool/day02/ex01/files/file2.txt")
                    .TransformContentToList();

            Dictionary dic = new Dictionary(wordOfFile1, wordOfFile2);
            dic.fillDictionary();
            dic.displayDictionary();
        } catch (IOException ex) {
            System.err.println("\u001B[31m" + "an error occurred when trying to read content from the file\n"
                    + ex.getMessage() + "\u001B[0m");
        }
    }
}

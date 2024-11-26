package day02.ex01;

import java.io.IOException;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        try {
            List<String> wordOfFile1 = ReadFile.read("day02/ex01/files/file1.txt")
                    .TransformContentToList();
            List<String> wordOfFile2 = ReadFile.read("day02/ex01/files/file2.txt")
                    .TransformContentToList();

            DictionaryOfWords dic = new DictionaryOfWords(wordOfFile1, wordOfFile2);
            dic.fillDictionary();
            // dic.displayDictionary();
            Vectors vec = new Vectors(dic);
            // vec.displayVectors();
            ReadFile.writeFile(dic.getDictionary());
            System.out.println("\u001B[32m" + "Similarity = " + vec.CosineSimilarity() + "\u001B[0m");
        } catch (IOException ex) {
            System.err.println("\u001B[31m" + ex.getMessage() + "\u001B[0m");
        } catch (CustomException ex) {
            System.err.println("\u001B[31m" + ex.getMessage() + "\u001B[0m");
        }
    }
}

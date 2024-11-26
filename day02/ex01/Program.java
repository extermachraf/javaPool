package day02.ex01;

import java.io.IOException;
import java.util.List;

public class Program {
    static void parse_args(String[] args) throws CustomException {
        if (args.length != 2)
            throw new CustomException("Invalid number of arguments. Expected 2 arguments.");
    }

    public static void main(String[] args) {
        try {
            parse_args(args);
            List<String> wordOfFile1 = ReadFile.read(args[0])
                    .TransformContentToList();
            List<String> wordOfFile2 = ReadFile.read(args[1])
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

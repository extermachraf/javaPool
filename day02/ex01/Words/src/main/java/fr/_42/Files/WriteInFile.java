package fr._42.Files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class WriteInFile {
    public static void WriteDictionary(Set<String> dic) throws IOException {
        String fileName = "dictionary.txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
            for (String word : dic) {
                bw.write(word);
                bw.newLine();
            }
        }
    }
}

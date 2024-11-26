package day02.ex01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ReadFile {
    static String content;

    public static ReadFile read(String path)
            throws IOException, CustomException {

        StringBuilder buildeText = new StringBuilder();
        boolean isFileEmpty = true;
        File file = new File(path);
        if (file.length() > 10 * 1024 * 1024)
            throw new CustomException("File size exceeds 10 MB");
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            buildeText.append(line);

            while (line != null) {
                if (line != null && !line.trim().isEmpty()) {
                    isFileEmpty = false;
                    buildeText.append(" ").append(line);
                }
                line = reader.readLine();
            }
            if (isFileEmpty)
                throw new CustomException("The File is Empty");
            ReadFile.content = buildeText.toString().toLowerCase();

            if (content.split("[^a-zA-Z]+").length == 0)
                throw new CustomException("the file contains no valid words");
        }
        return new ReadFile();
    }

    public List<String> TransformContentToList() {
        List<String> listOfWord = new ArrayList<String>();
        // split content based on any sequence of non-alphabetic characters.
        String[] splitContent = ReadFile.content.split("[^a-zA-Z]+");

        // fil list with splited content
        for (String word : splitContent) {
            if (!word.isEmpty()) {
                listOfWord.add(word);
            }
        }
        return listOfWord;
    }

    public static String getContent() {
        return content;
    }

    private ReadFile() {
    }

    public static void writeFile(List<String> dictionary) throws IOException {
        File file = new File("dictionary.txt");
        if (!file.exists())
            file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String word : dictionary) {
                writer.write(word);
                writer.newLine(); // Write each word on a new line
            }
        }
    }
}

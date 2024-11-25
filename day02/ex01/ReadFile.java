package day02.ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ReadFile {
    static String content;

    public static ReadFile read(String path) throws IOException {

        StringBuilder buildeText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            buildeText.append(line);

            while (line != null) {
                line = reader.readLine();
                if (line != null)
                    buildeText.append(" ").append(line);
            }
            ReadFile.content = buildeText.toString().toLowerCase();
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
}

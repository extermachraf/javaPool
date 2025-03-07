package fr._42.Files;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadFile {
    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024;
    private ReadFile() {}

    private static void checkFileSize(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (file.length() > MAX_FILE_SIZE_BYTES) {
                throw new IOException("file size exeeds the maximum allowed size (10MB)");
            }
        }
    }

    public static List<String> readWords(String path) throws IOException {
        checkFileSize(path);
        List<String> listOfWords = new ArrayList<String>();
        String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            while((line  = reader.readLine()) != null){
                String[] words = line.split("\\s+");
                Collections.addAll(listOfWords, words);
            }
            if (listOfWords.isEmpty())
                throw new RuntimeException("No words founds in the file");
            return listOfWords;
        }catch (IOException e){
            throw new FileNotFoundException("file not found");
        }
    }

}

package fr._42;

import fr._42.Files.Dictionary;
import fr._42.Files.ReadFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        try{
            List<String> words = ReadFile.readWords("ex01/Words/test.txt");
            List<String> words2 = ReadFile.readWords("ex01/Words/test1.txt");
            Dictionary dic = new Dictionary(words2, words);
            dic.displaySimilarity();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
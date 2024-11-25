package day02.ex01;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    List<String> file1;
    List<String> file2;
    List<String> Dictionary;

    public Dictionary(List<String> file1, List<String> file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    public void fillDictionary() {
        Set<String> uniqueWords = new HashSet<>(file1);
        uniqueWords.addAll(file2);
        Dictionary = new ArrayList<>(uniqueWords);
        Dictionary.sort(String::compareTo);
    }

    public void displayDictionary() {
        for (String word : Dictionary)
            System.out.println(word);
    }
}

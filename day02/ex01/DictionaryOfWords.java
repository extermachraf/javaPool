package day02.ex01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DictionaryOfWords {
    List<String> file1;
    List<String> file2;
    List<String> Dictionary;

    public DictionaryOfWords(List<String> file1, List<String> file2) {
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

    //geters
    public List<String> getWords(boolean wichFile){
        if (wichFile)
            return this.file2;
        else
            return this.file1;
    }

    public List<String> getDictionary(){
        return this.Dictionary;
    }
    
}

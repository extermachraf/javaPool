package fr._42.Files;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.List;

public class Dictionary {
    private final Set<String> dictionary = new HashSet<String>();
    private List<String> fileOne = null;
    private List<String> fileTwo = null;

    public Dictionary(List<String> fileOne, List<String> fileTwo) throws IOException {
        dictionary.addAll(fileOne);
        dictionary.addAll(fileTwo);
        WriteInFile.WriteDictionary(dictionary);
        this.fileOne = fileOne;
        this.fileTwo = fileTwo;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public void displaySimilarity() {
       int[] vectorA = getVector(fileOne);
       int[] vectorB =  getVector(fileTwo);

       double numerator = dotProduct(vectorA, vectorB);

       double magnitudeA = magnitude(vectorA);
       double magnitudeB = magnitude(vectorB);
       double dominator = magnitudeA * magnitudeB;

       double similarity = numerator / dominator;
       System.out.println("Similarity: " + similarity);
    }

    private int[] getVector(List<String> file){
        int[] vector = new int[dictionary.size()];
        List<String> dictionaryList = new ArrayList<>(dictionary);
        for (int i  = 0; i < dictionaryList.size(); i++){
            String word = dictionaryList.get(i);
            vector[i] = Collections.frequency(file, word);
        }
        return vector;
    }

    private double dotProduct(int[] vectorA, int[] vectorB) {
        double result = 0;
        for (int i  = 0; i < dictionary.size(); i++){
            result += vectorA[i] * vectorB[i];
        }
        return result;
    }

    private double magnitude(int[] vector) {
        double result = 0;
        for(int value : vector) {
            result += value * value;
        }
        return Math.sqrt(result);
    }
}

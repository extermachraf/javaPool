package day02.ex01;

import java.util.List;

public class Vectors {
    DictionaryOfWords dictionary;
    int[] vector1;
    int[] vector2;

    public Vectors(DictionaryOfWords dictionary) {
        this.dictionary = dictionary;
        this.vector1 = new int[dictionary.getDictionary().size()];
        this.vector2 = new int[dictionary.getDictionary().size()];
        this.fillVectors();
    }

    private void fillVectors() {
        List<String> dic = this.dictionary.getDictionary();
        List<String> file1 = this.dictionary.getWords(false);
        List<String> file2 = this.dictionary.getWords(true);

        for (String word : dic) {
            for (int i = 0; i < file1.size(); i++) {
                if (file1.get(i).equals(word))
                    vector1[dic.indexOf(word)]++;
            }
            for (int i = 0; i < file2.size(); i++) {
                if (file2.get(i).equals(word))
                    vector2[dic.indexOf(word)]++;
            }
        }
    }

    public void displayVectors() {
        System.out.println("Vector 1: ");
        for (int i = 0; i < vector1.length; i++) {
            System.out.print(vector1[i] + " ");
        }
        System.out.println();

        System.out.println("Vector 2: ");
        for (int i = 0; i < vector2.length; i++) {
            System.out.print(vector2[i] + " ");
        }
        System.out.println();
    }

    // calculate dotProduct

    private int dotProduct() {
        int dotProduct = 0;
        for (int i = 0; i < vector1.length; i++)
            dotProduct += vector1[i] * vector2[i];
        return dotProduct;
    }

    // calculate magnitude
    private double magnitude(int[] vector) {
        int sumeSquares = 0;
        for (int number : vector)
            sumeSquares += number * number;
        return Math.sqrt(sumeSquares);
    }

    // caluclate cosine similarity
    public double CosineSimilarity() {
        // claculate dote product
        int dotProduct = this.dotProduct();

        double magnitudeVector1 = this.magnitude(vector1);
        double magnitudeVector2 = this.magnitude(vector2);

        if (magnitudeVector1 == 0 || magnitudeVector2 == 0)
            return 0;
        return dotProduct / (magnitudeVector1 * magnitudeVector2);
    }

}

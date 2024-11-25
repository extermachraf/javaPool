package day02.ex01;
import java.util.List;

public class Vectors {
    DictionaryOfWords dictionary;
    int[] vector1;
    int[] vector2;


    public Vectors(DictionaryOfWords dictionary){
        this.dictionary = dictionary;
        this.vector1 = new int[dictionary.getDictionary().size()];
        this.vector2 = new int[dictionary.getDictionary().size()];
        this.fillVectors();
    }
    private void fillVectors(){
        List<String> dic = this.dictionary.getDictionary();
        List<String> file1 = this.dictionary.getWords(false);
        List<String> file2 = this.dictionary.getWords(true);

        for(String word : dic){
            for(int i = 0; i < file1.size(); i++){
                if(file1.get(i).equals(word))
                    vector1[dic.indexOf(word)]++;
            }
            for (int i = 0; i < file2.size(); i++) {
                if (file2.get(i).equals(word))  
                    vector2[dic.indexOf(word)]++; 
            }
        }
    }
    public void displayVectors() {
        System.out.println("Vector1:");
        for (int i = 0; i < vector1.length; i++) {
            System.out.print(vector1[i] + " ");
        }
        System.out.println(); 

        System.out.println("Vector2:");
        for (int i = 0; i < vector2.length; i++) {
            System.out.print(vector2[i] + " ");
        }
        System.out.println(); 
    }
}

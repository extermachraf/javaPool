package fr._42;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputAndOutput {
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public InputAndOutput() throws IOException {
        System.out.println("enter your fullname");
        String fullName = in.readLine();

        System.out.println("enter your age");
        int age = Integer
                .parseInt(in.readLine());


        System.out.println(fullName + " is " + age);
    }
    public static void main(String[] args) {
        try{
            int byteReader = System.in.read();
            System.out.println((char)byteReader);
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}

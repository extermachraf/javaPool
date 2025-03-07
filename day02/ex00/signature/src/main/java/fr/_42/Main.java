package fr._42;

import fr._42.handleSignatures.FileType;
import fr._42.handleSignatures.FileTypeIdentifier;
import fr._42.handleSignatures.FileWriter;
import fr._42.handleSignatures.ReadSignatures;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        StringBuilder output = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Map<FileType, String> types = ReadSignatures.storeTypes();

            String userInput;
            while(true){
                userInput = reader.readLine();
                if(userInput.equals("42"))
                    break;
                String fileType = FileTypeIdentifier.getFileType(userInput, types);
                output.append(fileType + "\n");
                System.out.println("PROCESSED");
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        }

        FileWriter writer = new FileWriter("result.txt");
        writer.writeToFile(output.toString());
    }
}
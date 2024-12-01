package ex02.ImagesToChar.src.java;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.IOException;

public class Program {
    @Parameter(names = "--black", description = "Black character", required = true)
    private String black;

    @Parameter(names = "--white", description = "White character", required = true)
    private String white;

    public static void main(String[] args) {
        Program program = new Program();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(program)
                .build();

        try {
            // Parse the command-line arguments
            jCommander.parse(args);

            // Validate the input arguments
            if (program.black.length() != 1 || program.white.length() != 1) {
                throw new CustomException("The arguments for --black and --white must each be a single character.");
            }

            // Convert parsed strings to characters
            char blackChar = program.black.charAt(0);
            char whiteChar = program.white.charAt(0);

            // Create the BmpPrint object and display the image
            BmpPrint bmp = new BmpPrint(blackChar, whiteChar);
            bmp.displayImage(bmp.imageToString());

        } catch (ParameterException pe) {
            // Display help message if arguments are incorrect or missing
            System.err.println("\u001B[31mERROR: Invalid arguments - " + pe.getMessage() + "\u001B[0m");
            jCommander.usage(); // Display usage information
        } catch (CustomException ex) {
            System.err.println("\u001B[31mERROR: " + ex.getMessage() + "\u001B[0m");
        } catch (IOException ex) {
            System.err.println("\u001B[31mERROR: " + ex.getMessage() + "\u001B[0m");
        }
    }
}

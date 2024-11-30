package ex01.ImagesToChar.src.java;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    private static final String EXPECTED_BLACK = "--black";
    private static final String EXPECTED_WHITE = "--white";

    private static char parseColore(String arg, String EXPECTED) throws CustomException {
        String[] splitarg = arg.split("=", 2);
        if (!splitarg[0].equals(EXPECTED))
            throw new CustomException(
                    "please enter the args in the followed format  '--black=<carater> --white=<caracter>'");
        if (splitarg[1].length() != 1) {
            throw new CustomException("The argument for " + EXPECTED + " must be a single character.");
        }
        return splitarg[1].charAt(0);

    }

    public static void main(String[] args) {
        try {
            if (args.length != 2)
                throw new CustomException(
                        "please enter the args in the followed format  '--black=<carater> --white=<caracter>'");
            char black = parseColore(args[0], EXPECTED_BLACK);
            char white = parseColore(args[1], EXPECTED_WHITE);

            BmpPrint bmp = new BmpPrint(black, white);

            bmp.displayImage(bmp.imageToString());

        } catch (CustomException ex) {
            System.err.println("\u001B[31mERROR: " + ex.getMessage() + "\u001B[0m");
        } catch (IOException ex) {
            System.err.println("\u001B[31mERROR: " + ex.getMessage() + "\u001B[0m");
        }
    }
}

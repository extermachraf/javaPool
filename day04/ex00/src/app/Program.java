package ex00.src.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ex00.src.logic.*;

public class Program {
    private static final String EXPECTED_BLACK = "--black";
    private static final String EXPECTED_WHITE = "--white";
    private static final String EXPECTED_IMAGE = "--image-path";

    private static char parseColore(String arg, String EXPECTED) throws CustomException {
        String[] splitarg = arg.split("=", 2);
        if (!splitarg[0].equals(EXPECTED))
            throw new CustomException(
                    "please enter the args in the followed format  '--black=<carater> --white=<caracter> --image-path=<relative path for image>'");
        if (splitarg[1].length() != 1) {
            throw new CustomException("The argument for " + EXPECTED + " must be a single character.");
        }
        return splitarg[1].charAt(0);

    }

    private static String parseImage(String arg, String EXPECTED_IMAGE) throws CustomException {
        String[] splitarg = arg.split("=", 2);
        if (!splitarg[0].equals(EXPECTED_IMAGE))
            throw new CustomException(
                    "please enter the args in the followed format  '--black=<carater> --white=<caracter> --image-path=<relative path for image>'");
        Path imagePath = Paths.get(splitarg[1]);
        if (!imagePath.toFile().exists()) {
            throw new CustomException("The file at " + splitarg[1] + " does not exist.");
        }
        if (!imagePath.isAbsolute())
            throw new CustomException("you need to provide the absolute path of image");
        return imagePath.toString();
    }

    public static void main(String[] args) {
        try {
            if (args.length != 3)
                throw new CustomException(
                        "please enter the args in the followed format  '--black=<carater> --white=<caracter> --image-path=<relative path for image>'");
            char black = parseColore(args[0], EXPECTED_BLACK);
            char white = parseColore(args[1], EXPECTED_WHITE);
            String image = parseImage(args[2], EXPECTED_IMAGE);

            BmpPrint bmp = new BmpPrint(black, white, image);

            bmp.displayImage(bmp.imageToString());

        } catch (CustomException ex) {
            System.err.println("\u001B[31mERROR: " + ex.getMessage() + "\u001B[0m");
        } catch (IOException ex) {
            System.err.println("\u001B[31mERROR: " + ex.getMessage() + "\u001B[0m");
        }
    }
}

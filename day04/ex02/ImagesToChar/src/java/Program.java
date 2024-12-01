package ex02.ImagesToChar.src.java;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.diogonunes.jcdp.color.api.Ansi;
import com.diogonunes.jcdp.color.ColoredPrinter;

import java.io.IOException;

public class Program {
    @Parameter(names = "--foreground", description = "Foreground color", required = true)
    private String foregroundColor;

    @Parameter(names = "--background", description = "Background color", required = true)
    private String backgroundColor;

    public static void main(String[] args) {
        Program program = new Program();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(program)
                .build();

        try {
            // Parse the command-line arguments
            jCommander.parse(args);

            // Validate the input arguments for color names
            Ansi.BColor foreground = getBackgroundColor(program.foregroundColor);
            Ansi.BColor background = getBackgroundColor(program.backgroundColor);

            // Print the values of the two colors
            System.out.println("Foreground color: " + foreground);
            System.out.println("Background color: " + background);
            // Create the BmpPrint object and display the image
            BmpPrint bmp = new BmpPrint(foreground, background);
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

    private static Ansi.FColor getForegroundColor(String color) {
        switch (color.toUpperCase()) {
            case "RED":
                return Ansi.FColor.RED;
            case "YELLOW":
                return Ansi.FColor.YELLOW;
            case "GREEN":
                return Ansi.FColor.GREEN;
            case "BLUE":
                return Ansi.FColor.BLUE;
            case "MAGENTA":
                return Ansi.FColor.MAGENTA;
            default:
                return Ansi.FColor.WHITE; // Default to white if unrecognized
        }
    }

    private static Ansi.BColor getBackgroundColor(String color) {
        switch (color.toUpperCase()) {
            case "BLACK":
                return Ansi.BColor.BLACK;
            case "RED":
                return Ansi.BColor.RED;
            case "GREEN":
                return Ansi.BColor.GREEN;
            case "BLUE":
                return Ansi.BColor.BLUE;
            case "MAGENTA":
                return Ansi.BColor.MAGENTA;
            default:
                return Ansi.BColor.BLACK; // Default to black if unrecognized
        }
    }
}

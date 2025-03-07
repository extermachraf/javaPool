package day02.ex02;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Program {
    private static final String EXPECTED_ARG = "--current-folder";

    public static String parse_args(String[] arguments) throws CustomException {
        String message = "The program expects exactly one argument in the format: --current-folder=path-to-the-workdir";
        if (arguments.length != 1)
            throw new CustomException(message);
        String[] splitarg = arguments[0].split("=", 2);

        if (splitarg.length != 2 || !EXPECTED_ARG.equals(splitarg[0]))
            throw new CustomException(message);
        return splitarg[1];
    }

    public static void isValideDirectoryPath(String path) throws CustomException {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory())
            throw new CustomException("The provided path is not a valid directory: " + path);

    }

    public static void main(String args[]) {
        try {
            String pathToWorkDir = parse_args(args);
            System.err.println(pathToWorkDir);
            isValideDirectoryPath(pathToWorkDir);

            Shell shell = new Shell(pathToWorkDir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.print("\u001B[33m--> \u001B[0m");
                String line = reader.readLine();
                // use a classe that handle the cmd line for the minishell
                try {
                    shell.handleCmds(line);
                } catch (Exception e) {
                    System.out.println("Permission denied");
                }
            }
        } catch (CustomException ex) {
            System.err.println("\u001B[31mERROR : " + ex.getMessage() + "\u001B[0m");
        } catch (IOException ex) {
            System.err.println("n");
        }
    }
}

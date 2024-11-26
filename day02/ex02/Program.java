package day02.ex02;

import java.io.File;

public class Program {
    private static final String EXPECTED_ARG = "--current-folder";

    public static String pars_args(String[] arguments) throws CustomException {
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
            String pathToWorkDir = pars_args(args);
            System.err.println(pathToWorkDir);
            isValideDirectoryPath(pathToWorkDir);
        } catch (CustomException ex) {
            System.err.println("\u001B[31mERROR : " + ex.getMessage() + "\u001B[0m");
        }
    }
}

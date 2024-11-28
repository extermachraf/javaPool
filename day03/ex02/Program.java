// https://www.freecodecamp.org/news/java-multithreading-producer-consumer-problem/#heading-what-is-the-producer-consumer-problem
package day03.ex02;

import day03.ex00.CustomException;
import java.lang.NumberFormatException;

public class Program {
    private static final String EXPECTED_ARG_COUNT = "--arraySize";
    private static final String EXPECTED_ARG_THREAD = "--threadsCount";

    private static int parseArg(String arg, String expectedArg) throws CustomException, NumberFormatException {
        String[] splitarg = arg.split("=", 2);
        if (!splitarg[0].equals(expectedArg) || !splitarg[1].matches("\\d+")) {
            throw new CustomException(
                    "Please provide the argument in the format: --arraySize=<number> --threadsCount=<number>");
        }
        return Integer.parseInt(splitarg[1]);
    }

    public static void main(String[] args) {
        try {
            if (args.length != 2)
                throw new CustomException(
                        "Please provide the arguments in the format: --arraySize=<number> --threadsCount=<number>");
            int arraySize = parseArg(args[0], EXPECTED_ARG_COUNT);
            int threadCount = parseArg(args[1], EXPECTED_ARG_THREAD);
            if (threadCount > arraySize)
                throw new CustomException("the number of threads should not be greater than array size");

        } catch (CustomException ex) {
            System.err.println("\u001B[31mError : " + ex.getMessage() + "\u001B[0m");
        } catch (NumberFormatException ex) {
            System.err.println("\u001B[31mError: you provided a large number that can't be handled\u001B[0m");
        }
    }
}

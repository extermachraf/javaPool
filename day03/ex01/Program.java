// https://www.freecodecamp.org/news/java-multithreading-producer-consumer-problem/#heading-what-is-the-producer-consumer-problem
package day03.ex01;

import day03.ex00.CustomException;
import java.lang.NumberFormatException;

public class Program {
    private static final String EXPECTED_ARG = "--count";

    private static int parseArgs(String[] args) throws CustomException, NumberFormatException {
        if (args.length != 1)
            throw new CustomException("Please provide the argument in the format: --count=<number>");
        String[] splitarg = args[0].split("=", 2);
        if (!splitarg[0].equals(EXPECTED_ARG) || !splitarg[1].matches("\\d+")) {
            throw new CustomException("Please provide the argument in the format: --count=<number>");
        }
        return Integer.parseInt(splitarg[1]);
    }

    public static void main(String[] args) {
        try {
            int count = parseArgs(args);
            Object lock = new Object();
            Egg egg = new Egg(count, lock);
            Hen hen = new Hen(count, lock);
            Thread eggs = new Thread(egg);
            Thread hens = new Thread(hen);

            eggs.start();
            hens.start();

        } catch (CustomException ex) {
            System.err.println("\u001B[31mError : " + ex.getMessage() + "\u001B[0m");
        } catch (NumberFormatException ex) {
            System.err.println("\u001B[31mError: you provided a large number that can't be handled\u001B[0m");
        }
    }
}

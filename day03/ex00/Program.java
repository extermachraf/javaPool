package day03.ex00;

import java.lang.NumberFormatException;

public class Program {
    // parse argument
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
            System.out.println("count = " + count);
            Egg egg = new Egg(count);
            Hen hen = new Hen(count);
            Thread eggs = new Thread(egg);
            Thread hens = new Thread(hen);

            eggs.start();
            hens.start();

            eggs.join();
            hens.join();

            for (int i = 0; i < count; i++) {
                System.out.println("Human");
            }

        } catch (CustomException ex) {
            System.err.println("\u001B[31mError : " + ex.getMessage() + "\u001B[0m");
        } catch (NumberFormatException ex) {
            System.err.println("\u001B[31mError: you provided a large number that can't be handled\u001B[0m");
        } catch (InterruptedException ex) {
            System.err.println("\u001B[31mError: an error occured\u001B[0m");
        }
    }
}

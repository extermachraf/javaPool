// https://www.freecodecamp.org/news/java-multithreading-producer-consumer-problem/#heading-what-is-the-producer-consumer-problem
package day03.ex02;

import day03.ex00.CustomException;
import java.lang.NumberFormatException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Program {
    private static final String EXPECTED_ARG_COUNT = "--arraySize";
    private static final String EXPECTED_ARG_THREAD = "--threadsCount";

    private static final AtomicInteger totalSum = new AtomicInteger(0);

    private static int parseArg(String arg, String expectedArg) throws CustomException, NumberFormatException {
        String[] splitarg = arg.split("=", 2);
        if (!splitarg[0].equals(expectedArg) || !splitarg[1].matches("\\d+")) {
            throw new CustomException(
                    "Please provide the argument in the format: --arraySize=<number> --threadsCount=<number>");
        }
        return Integer.parseInt(splitarg[1]);
    }

    // generate random array
    private static int[] randomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];

        for (int i = 0; i < array.length; i++)
            array[i] = rand.nextInt(2001) - 1000;

        return array;
    }

    // print the sum of array
    private static void printSum(int[] array) {
        int sum = 0;
        for (int i : array)
            sum += i;

        System.out.println("Sum: " + sum);
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
            int chunkSize = arraySize / threadCount;

            // random array
            int[] randomArray = randomArray(arraySize);
            printSum(randomArray);

            Thread[] threads = new Thread[threadCount];
            for (int i = 0; i < threadCount; i++) {
                int start = i * chunkSize;
                int end = (i == threadCount - 1) ? arraySize : (i + 1) * chunkSize;

                threads[i] = new Thread(new OuterClass.SumTask(randomArray, start, end, totalSum, i + 1));
                threads[i].start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println("Sum by threads: " + totalSum.get());

        } catch (CustomException ex) {
            System.err.println("\u001B[31mError : " + ex.getMessage() + "\u001B[0m");
        } catch (NumberFormatException ex) {
            System.err.println("\u001B[31mError: you provided a large number that can't be handled\u001B[0m");
        } catch (InterruptedException ex) {
            System.err.println("\u001B[31mError: an error occured\u001B[0m");
        }
    }
}

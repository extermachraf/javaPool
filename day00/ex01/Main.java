package day00.ex01;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a number (Ctrl+C to exit):");
        while (true) {
            System.out.print("-> ");
            if (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                if (number <= 1) {
                    System.err.println("IllegalArgument");
                    scanner.close();
                    System.exit(-1);
                }
                int iterator_counter = 0;
                int sq = (int) Math.sqrt(number);
                boolean isPrime = true;
                for (int i = 2; i <= sq; i++) {
                    iterator_counter++;
                    if (number % i == 0) {
                        isPrime = false;
                        System.out.println(iterator_counter + " false");
                        break;
                    }
                }
                if (isPrime) {
                    System.out.println(iterator_counter + " true");
                }
            } else {
                // Handle case where the input is not a number
                System.err.println("Please enter a valid number.");
                scanner.close();
                System.exit(-1);
            }
        }
    }
}

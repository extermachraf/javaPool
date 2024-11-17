
/**
 * The Program class implements an application that reads an integer from the user
 * and determines if it is a prime number. If the input number is less than or equal to 1,
 * the program will terminate with an error message. For valid inputs, it will check the
 * primality of the number and print the number of iterations it took to determine the result.
 * 
 * <p>The program continuously prompts the user for input until terminated using Ctrl+C.
 * If the input is not a valid integer, the program will terminate with an error message.
 * 
 * <p>Usage:
 * <pre>
 * Enter a number (Ctrl+C to exit):
 * -> 5
 * 2 true
 * -> 4
 * 1 false
 * </pre>
 * 
 * <p>Note: The program uses a Scanner to read input from the console and checks for
 * integer inputs. It calculates the square root of the number to optimize the prime
 * checking process.
 * 
 * <p>Exceptions:
 * <ul>
 * <li>IllegalArgumentException - if the input number is less than or equal to 1.</li>
 * <li>InputMismatchException - if the input is not a valid integer.</li>
 * </ul>
 */
package day00.ex01;

import java.util.Scanner;

public class Program {
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

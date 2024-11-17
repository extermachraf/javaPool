
/**
 * The Program class provides functionality to check if the sum of digits of a given number is prime,
 * and counts the number of such occurrences based on user input.
 * 
 * Methods:
 * - isPrime(int number): Checks if a given number is prime.
 * - sum_of_digits(int number): Calculates the sum of the digits of a given number.
 * - main(String[] args): The main method that drives the program, taking user input and processing it.
 * 
 * The program continuously prompts the user for input and performs the following actions:
 * - If the input is less than or equal to 1, it prints an error message and exits.
 * - If the input is 42, it prints the count of coffee requests and exits.
 * - If the sum of the digits of the input is a prime number, it increments the coffee request count.
 * - If the input is not a valid integer, it prints an error message and exits.
 */
package day00.ex02;

import java.util.Scanner;

public class Program {

    static boolean isPrime(int number) {
        int sq = (int) Math.sqrt(number);
        boolean isPrime = true;
        for (int i = 2; i <= sq; i++) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }

    static int sum_of_digits(int number) {
        int sum = 0;
        while (number > 0) {
            int digit = number % 10;
            sum += digit;
            number /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cofee_request = 0;
        while (true) {
            System.out.print("--> ");
            if (scanner.hasNextInt()) {
                int query = scanner.nextInt();
                if (query <= 1) {
                    System.err.println("IllegalArgument");
                    scanner.close();
                    System.exit(-1);
                } else if (query == 42) {
                    System.out.println("Count of coffee-request : " + cofee_request);
                    System.exit(1);
                } else if (isPrime(sum_of_digits(query)))
                    cofee_request++;
            } else {
                System.err.println("Please enter a valid query");
                scanner.close();
                System.exit(-1);
            }
        }

    }
}

/*
 * Program: Coffee Query Counter
 * Description:
 * This program continuously reads a sequence of natural numbers from the user. 
 * It calculates the sum of the digits for each number and checks whether the sum 
 * is a prime number. If the sum is a prime number, the program increments a counter.
 * The program stops when the number 42 is entered, at which point it outputs the 
 * total count of coffee-related queries (i.e., those with a prime sum of digits).
 *
 * A valid query must be a natural number greater than 1. If the sum of the digits 
 * of the number is a prime number, it is considered a coffee request. If an invalid 
 * number or non-numeric input is provided, the program will terminate with an error message.
 *
 * Input:
 * - Natural numbers (greater than 1), one per line.
 * Output:
 * - A count of coffee-related queries, displayed when the number 42 is entered.
 *
 * Example:
 * 
 * Input:
 * --> 198131
 * --> 12901212
 * --> 11122
 * --> 42
 *
 * Output:
 * Count of coffee-request : 2
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

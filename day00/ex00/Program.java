
/**
 * The Program class contains a main method that calculates the sum of the digits of a given integer.
 * 
 * The main method initializes an integer variable with a specific value and then uses a while loop
 * to iterate through each digit of the number. In each iteration, it extracts the last digit of the number,
 * adds it to a sum variable, and then removes the last digit from the number. Finally, it prints the sum of the digits.
 * 
 * This program demonstrates basic control flow and arithmetic operations in Java.
 */
package day00.ex00;

public class Program {
    public static void main(String[] args) {
        int number = 45678;
        int sum = 0;
        while (number > 0) {
            int digit = number % 10;
            sum += digit;
            number = number / 10;
        }
        System.out.println("the sum of the digits is : " + sum);
    }
}
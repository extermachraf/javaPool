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
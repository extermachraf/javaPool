/**
 * This file contains the NumberWorker class which provides methods to check if a number is prime and to calculate the sum of the digits of a number.
 */
package numbers.classes;

import numbers.exeptions.IllegalNumberException;

public class NumberWorker {

    public static boolean isPrime(int number) throws IllegalNumberException {
        if (number <= 1) {
            throw new IllegalNumberException("number should be greater than 1");
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int digitsSum(int number) {
        if (number == 0) {
            return 0;
        }
        number = Math.abs(number);
        return (number % 10) + digitsSum(number / 10);
    }
}

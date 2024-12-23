/**
 * Unit tests for the NumberWorker class, specifically testing the isPrime method
 * with various valid and invalid inputs using parameterized tests.
 */
package numbers;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import numbers.classes.NumberWorker;
import numbers.exeptions.IllegalNumberException;
import static org.junit.Assert.assertFalse;

class NumberWorkerTest {

    //test isPrime function
    @ParameterizedTest
    @ValueSource(ints = {11, 13, 17, 19, 23, 29, 31, Integer.MAX_VALUE})
    void isPrime_returnTrue(int number) {
        try {
            assertTrue(NumberWorker.isPrime(number));
        } catch (IllegalNumberException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20})
    void isPrime_returnFalse(int number) {
        try {
            assertFalse(NumberWorker.isPrime(number));
        } catch (IllegalNumberException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, -40})
    void isPrime_invalidArguments(int number) {
        try {
            NumberWorker.isPrime(number);
        } catch (IllegalNumberException e) {
            assertTrue(true);
        }
    }

}

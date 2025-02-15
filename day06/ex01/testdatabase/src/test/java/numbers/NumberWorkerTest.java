package numbers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import numbers.classes.NumberWorker;
import numbers.exeptions.IllegalNumberException;

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

    @ParameterizedTest
    @CsvFileSource(resources = "/validSumOfDigit.csv", numLinesToSkip = 1)
    void digitsSum_ShouldGenerateTheExpectedOutput(int input, int expectedOutput) {
        assertEquals(expectedOutput, NumberWorker.digitsSum(input));
    }
}

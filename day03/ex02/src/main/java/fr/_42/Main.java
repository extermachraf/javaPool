package fr._42;

public class Main {
    private static int[] array;
    private static int arraySize;
    private static int threadsCount;
    private static int sumByThreads = 0;

    public static void main(String[] args) {
        try {
            parseArguments(args);
            initializeArray();
            int standardSum = calculateStandardSum();

            System.out.println("Sum: " + standardSum);

            calculateSumWithThreads();

            System.out.println("Sum by threads: " + sumByThreads);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private static void parseArguments(String[] args) {
        boolean arraySizeSet = false;
        boolean threadsCountSet = false;

        for (String arg : args) {
            if (arg.startsWith("--arraySize=")) {
                try {
                    arraySize = Integer.parseInt(arg.substring(12));
                    if (arraySize <= 0 || arraySize > 2000000) {
                        throw new IllegalArgumentException("Array size must be between 1 and 2,000,000");
                    }
                    arraySizeSet = true;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid array size format");
                }
            } else if (arg.startsWith("--threadsCount=")) {
                try {
                    threadsCount = Integer.parseInt(arg.substring(15));
                    if (threadsCount <= 0) {
                        throw new IllegalArgumentException("Thread count must be positive");
                    }
                    threadsCountSet = true;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid thread count format");
                }
            }
            else
                throw new IllegalArgumentException("Unknown argument: " + arg);
        }

        if (!arraySizeSet || !threadsCountSet) {
            throw new IllegalArgumentException("Both --arraySize and --threadsCount must be specified");
        }

        // Check if threadsCount is greater than arraySize
        if (threadsCount > arraySize) {
            throw new IllegalArgumentException("Thread count cannot be greater than array size");
        }
    }

    private static void initializeArray() {
        array = new int[arraySize];

        // Initialize all elements to 1
        for (int i = 0; i < arraySize; i++) {
            array[i] = 1;
        }
    }

    private static int calculateStandardSum() {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private static void calculateSumWithThreads() {
        Thread[] threads = new Thread[threadsCount];
        SumThread[] sumThreads = new SumThread[threadsCount];

        int elementsPerThread = arraySize / threadsCount;
        int remainingElements = arraySize % threadsCount;

        int startIndex = 0;

        for (int i = 0; i < threadsCount; i++) {
            int threadElements = elementsPerThread;
            if (i < remainingElements) {
                threadElements++;
            }

            int endIndex = startIndex + threadElements - 1;

            sumThreads[i] = new SumThread(i + 1, startIndex, endIndex);
            threads[i] = new Thread(sumThreads[i]);

            threads[i].start();
            startIndex = endIndex + 1;
        }

        // Wait for all threads to complete
        try {
            for (Thread thread : threads) {
                thread.join();
            }

            // Calculate total sum from all threads
            for (SumThread sumThread : sumThreads) {
                sumByThreads += sumThread.getThreadSum();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class SumThread implements Runnable {
        private final int threadNumber;
        private final int startIndex;
        private final int endIndex;
        private int threadSum;

        public SumThread(int threadNumber, int startIndex, int endIndex) {
            this.threadNumber = threadNumber;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadSum = 0;
        }

        @Override
        public void run() {
            for (int i = startIndex; i <= endIndex; i++) {
                threadSum += array[i];
            }

            System.out.println("Thread " + threadNumber + ": from " + startIndex +
                    " to " + endIndex + " sum is " + threadSum);
        }

        public int getThreadSum() {
            return threadSum;
        }
    }
}
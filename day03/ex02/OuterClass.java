package day03.ex02;

import java.util.concurrent.atomic.AtomicInteger;

public class OuterClass {
    static class SumTask implements Runnable {
        private final int[] array;
        private final int start;
        private final int end;
        private final AtomicInteger totalSum;
        private final int threadNumber;

        SumTask(int[] array, int start, int end, AtomicInteger totalSum, int threadNumber) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.totalSum = totalSum;
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            // Code to be executed in the run method
            int partialSum = returnSum();

            totalSum.addAndGet(partialSum);
            System.out.printf("Thread %d: from %d to %d sum is %d%n", threadNumber, start, end - 1, partialSum);

        }

        public int returnSum() {
            int sum = 0;
            for (int i = this.start; i < this.end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }
}

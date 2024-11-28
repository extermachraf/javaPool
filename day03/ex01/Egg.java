package day03.ex01;

public class Egg implements Runnable {
    private int counter;
    private final Object lock;

    public Egg(int counter, Object lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < counter; i++) {
            synchronized (lock) {
                System.out.println("Egg");
                // Notify the other thread (Hen) to proceed
                lock.notify();
                try {
                    // Wait for the Hen thread to finish before printing the next "Egg"
                    if (i < counter - 1) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

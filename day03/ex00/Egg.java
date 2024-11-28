package day03.ex00;

public class Egg implements Runnable {
    int counter;

    Egg(int counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < counter; i++) {
            System.out.println("Egg");
        }
    }
}

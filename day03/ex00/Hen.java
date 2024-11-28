package day03.ex00;

public class Hen implements Runnable {
    int counter;

    Hen(int counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < counter; i++) {
            System.out.println("Hen");
        }
    }
}

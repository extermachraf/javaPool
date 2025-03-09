package fr._42.implementRunnable;

public class EggRunnable implements Runnable {
    private final int count;

    public EggRunnable(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Egg");
        }
    }
}

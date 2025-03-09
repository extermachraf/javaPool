package fr._42.extending;

public class EggThread extends Thread {
    private int count;

    // Constructor that takes the count parameter
    public EggThread(int count) {
        this.count = count;
    }

    // The run method is what the thread executes when started
    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Egg");
        }
    }
}


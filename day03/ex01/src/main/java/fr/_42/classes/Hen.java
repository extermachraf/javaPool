package fr._42.classes;

public class Hen implements Runnable {

    private final int count;
    private final ShareState state;

    public Hen(int count, ShareState shareState) {
        this.count = count;
        this.state = shareState;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            state.printHen();
        }
    }
}

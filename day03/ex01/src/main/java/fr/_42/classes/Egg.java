package fr._42.classes;

public class Egg implements Runnable{

    private final int count;
    private final ShareState state;


    public Egg(int count, ShareState shareState) {
        this.count = count;
        this.state = shareState;
    }
    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            state.printEgg();
        }
    }
}

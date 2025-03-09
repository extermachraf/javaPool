package fr._42.classes;

public class ShareState {
    private boolean eggTurn = true;

    public synchronized void printEgg() {
        while (!eggTurn) {
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Egg");
        eggTurn = false;

        notify();
    }


    public synchronized void printHen(){
        while (eggTurn){
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Hen");
        eggTurn = true;

        notify();
    }
}

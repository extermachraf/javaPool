package fr._42;


import fr._42.extending.EggThread;
import fr._42.extending.HenThread;
import fr._42.implementRunnable.EggRunnable;
import fr._42.implementRunnable.HenRunnable;

import javax.management.BadAttributeValueExpException;

public class Main {
    private static int parseArgs(String[] args) throws BadAttributeValueExpException {

        if (args.length != 1)
            throw new BadAttributeValueExpException("the program expects exactly one argument");
        String[] splitArg = args[0].split("=");
        if (splitArg.length != 2 || !splitArg[0].equals("--count") || !splitArg[1].matches("^[0-9]+$"))
            throw new BadAttributeValueExpException("the program accept one argument in the form of --count=<number>");
        return Integer.parseInt(splitArg[1]);
    }

    public static void main(String[] args) {

        try {
            int count = parseArgs(args);


            Runnable eggRunnable = new EggRunnable(count);
            Runnable henRunnable = new HenRunnable(count);

            Thread eggThread = new Thread(eggRunnable);
            Thread henThread = new Thread(henRunnable);

            eggThread.start();
            henThread.start();

            try{
                eggThread.join();
                henThread.join();

                for(int i = 0; i < count; i++)
                    System.out.println("Human");
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
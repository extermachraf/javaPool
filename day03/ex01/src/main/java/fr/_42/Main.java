package fr._42;

import fr._42.classes.Egg;
import fr._42.classes.Hen;
import fr._42.classes.ShareState;

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
        try{
            int count = parseArgs(args);
            ShareState state = new ShareState();


            Runnable EggRunnable = new Egg(count, state);
            Runnable HenRunnable = new Hen(count, state);

            Thread EggThread = new Thread(EggRunnable);
            Thread HenThread = new Thread(HenRunnable);

            EggThread.start();
            HenThread.start();
            EggThread.join();
            HenThread.join();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
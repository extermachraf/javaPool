package day01.ex05;

import day01.ex04.TransactionsService;

public class Program {
    public static void main(String[] args) {
        try {
            // Default mode is production
            boolean isDevMode = false;

            // Check if the argument matches the dev profile flag
            if (args.length > 0) {
                if (args[0].equals("--profile=dev")) {
                    isDevMode = true;
                } else {
                    System.err.println("\u001B[31mInvalid argument. Use '--profile=dev' for developer mode.\u001B[0m");
                    throw new IllegalArgumentException("Invalid argument. Use '--profile=dev' for developer mode.");
                }
            }

            // Initialize TransactionsService and Menu
            TransactionsService transactionsService = new TransactionsService();
            Menu menu = new Menu(transactionsService, isDevMode);

            // Start the menu interaction
            menu.handleUserInput();
        } catch (IllegalArgumentException e) {
            System.err.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}

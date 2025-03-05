package day01.ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        // Create a user
        User user = new User(1, "John", 500.0);

        // Add transactions
        user.addTransaction(new Transaction(200.0));
        user.addTransaction(new Transaction(-50.0));
        user.addTransaction(new Transaction(100.0));

        // Display transactions
        System.out.println("User Transactions:");
        user.displayTransactions();

        // Remove a transaction by ID
        UUID transactionIdToRemove = user.getTransactions()[1].getId();
        user.removeTransaction(transactionIdToRemove);

        // Display transactions after removal
        System.out.println("\nUser Transactions after removal:");
        user.displayTransactions();
    }
}

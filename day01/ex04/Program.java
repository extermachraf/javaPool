package day01.ex04;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        // Create the TransactionsService to manage users and transactions
        TransactionsService service = new TransactionsService();

        // Add users
        User alice = new User("Alice", 1000);
        User bob = new User("Bob", 500);
        User charlie = new User("Charlie", 300);

        service.addUser(alice);
        service.addUser(bob);
        service.addUser(charlie);

        // Display initial balances
        System.out.println("Initial Balances:");
        System.out.println(alice.getName() + "'s balance: " + service.getUserBalance(alice.getId()));
        System.out.println(bob.getName() + "'s balance: " + service.getUserBalance(bob.getId()));
        System.out.println(charlie.getName() + "'s balance: " + service.getUserBalance(charlie.getId()));
        System.out.println("------------------------------------------------");

        // Perform a transfer: Alice -> Bob (200)
        System.out.println("Performing a transfer: Alice -> Bob (200)");
        service.performTransfer(alice.getId(), bob.getId(), 200);

        // Display balances after the transfer
        System.out.println("Balances after Alice -> Bob transfer:");
        System.out.println(alice.getName() + "'s balance: " + service.getUserBalance(alice.getId()));
        System.out.println(bob.getName() + "'s balance: " + service.getUserBalance(bob.getId()));
        System.out.println("------------------------------------------------");

        // Perform another transfer: Bob -> Charlie (100)
        System.out.println("Performing a transfer: Bob -> Charlie (100)");
        service.performTransfer(bob.getId(), charlie.getId(), 100);

        // Display balances after the second transfer
        System.out.println("Balances after Bob -> Charlie transfer:");
        System.out.println(bob.getName() + "'s balance: " + service.getUserBalance(bob.getId()));
        System.out.println(charlie.getName() + "'s balance: " + service.getUserBalance(charlie.getId()));
        System.out.println("------------------------------------------------");

        // Check Alice's transactions
        System.out.println("Alice's transactions:");
        for (Transaction transaction : service.getUserTransactions(alice.getId())) {
            System.out.println("Transaction ID: " + transaction.getId() +
                               ", Type: " + transaction.getType() +
                               ", Amount: " + transaction.getAmount() +
                               ", Recipient: " + transaction.getRecipient().getName());
        }
        System.out.println("------------------------------------------------");

        // Check Bob's transactions
        System.out.println("Bob's transactions:");
        for (Transaction transaction : service.getUserTransactions(bob.getId())) {
            System.out.println("Transaction ID: " + transaction.getId() +
                               ", Type: " + transaction.getType() +
                               ", Amount: " + transaction.getAmount() +
                               ", Sender: " + transaction.getSender().getName());
        }
        System.out.println("------------------------------------------------");

        // Attempt an invalid transaction (Charlie -> Alice for 400)
        System.out.println("Attempting an invalid transfer: Charlie -> Alice (400)");
        try {
            service.performTransfer(charlie.getId(), alice.getId(), 400);
        } catch (IllegalTransactionException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("------------------------------------------------");

        // Perform a valid transfer: Charlie -> Alice (100)
        System.out.println("Performing a transfer: Charlie -> Alice (100)");
        service.performTransfer(charlie.getId(), alice.getId(), 100);

        // Display balances after Charlie -> Alice transfer
        System.out.println("Balances after Charlie -> Alice transfer:");
        System.out.println(alice.getName() + "'s balance: " + service.getUserBalance(alice.getId()));
        System.out.println(charlie.getName() + "'s balance: " + service.getUserBalance(charlie.getId()));
        System.out.println("------------------------------------------------");

        // Remove a transaction from Alice (first transaction ID)
        Transaction[] aliceTransactions = service.getUserTransactions(alice.getId());
        UUID transactionToRemove = aliceTransactions[0].getId();
        System.out.println("Removing Alice's transaction with ID: " + transactionToRemove);
        service.removeTransaction(alice.getId(), transactionToRemove);

        // Display Alice's transactions after removal
        System.out.println("Alice's transactions after removal:");
        for (Transaction transaction : service.getUserTransactions(alice.getId())) {
            System.out.println("Transaction ID: " + transaction.getId() +
                               ", Type: " + transaction.getType() +
                               ", Amount: " + transaction.getAmount() +
                               ", Recipient: " + transaction.getRecipient().getName());
        }
        System.out.println("------------------------------------------------");

        // Test unpaired transactions
        System.out.println("Checking for unpaired transactions:");
        Transaction[] unpairedTransactions = service.checkUnpairedTransactions();
        if (unpairedTransactions.length == 0) {
            System.out.println("No unpaired transactions found.");
        } else {
            for (Transaction unpaired : unpairedTransactions) {
                System.out.println("Unpaired Transaction ID: " + unpaired.getId() +
                                   ", Type: " + unpaired.getType() +
                                   ", Amount: " + unpaired.getAmount());
            }
        }
        System.out.println("------------------------------------------------");

        // Summary of final balances
        System.out.println("Final Balances:");
        System.out.println(alice.getName() + "'s balance: " + service.getUserBalance(alice.getId()));
        System.out.println(bob.getName() + "'s balance: " + service.getUserBalance(bob.getId()));
        System.out.println(charlie.getName() + "'s balance: " + service.getUserBalance(charlie.getId()));
        System.out.println("------------------------------------------------");
    }
}


package day01.ex03;

import java.util.UUID;

public class User {
    private int id;
    private String name;
    private double balance;
    private TransactionsLinkedList transactions;

    public User(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.transactions = new TransactionsLinkedList(); // Initialize the transactions list
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.addTransaction(transaction);
    }

    public void removeTransaction(UUID id) throws TransactionNotFoundException {
        transactions.removeTransactionById(id);
    }

    public Transaction[] getTransactions() {
        return transactions.toArray();
    }

    public void displayTransactions() {
        Transaction[] allTransactions = transactions.toArray();
        for (Transaction transaction : allTransactions) {
            System.out.println(
                    "Transaction ID: " + transaction.getId() + ", Amount: " + transaction.getAmountOfTransaction());
        }
    }
}

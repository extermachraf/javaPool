package day01.ex04;

import java.util.UUID;

public class User {
    private int id;
    private String name;
    private double balance;
    private TransactionsList transactionsList;

    private static int idCounter = 0;

    public User(String name, double balance) {
        this.id = ++idCounter;
        this.name = name;
        setBalance(balance);
        this.transactionsList = new TransactionsLinkedList();
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

    public void setBalance(double balance) throws IllegalArgumentException {
        if (balance < 0)
            throw new IllegalArgumentException("balanace of user can't be negative");
        this.balance = balance;
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }
}

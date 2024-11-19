package day01.ex04;

import java.util.UUID;

public class User {
    private int id;
    private String name;
    private int balance;
    private TransactionsList transactionsList;

    private static int idCounter = 0;

    public User(String name, int balance) {
        this.id = ++idCounter;
        this.name = name;
        this.balance = balance;
        this.transactionsList = new TransactionsLinkedList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }
}

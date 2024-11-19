package day01.ex03;

import java.util.UUID;

public class Transaction {
    private UUID id;
    private double amount;
    private Transaction nexTransaction;

    public Transaction(double amount) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.nexTransaction = null;
    }

    public UUID getId() {
        return this.id;
    }

    public double getAmountOfTransaction() {
        return this.amount;
    }

    public Transaction getnexTransaction() {
        return nexTransaction;
    }

    public void setNextTransaction(Transaction nexTransaction) {
        this.nexTransaction = nexTransaction;
    }

}

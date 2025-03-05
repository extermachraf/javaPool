package day01.ex04;

import java.util.UUID;

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private int amount;
    private TransactionType type;
    private Transaction nextTransaction;

    public Transaction(UUID id, User sender, User recipient, TransactionType type, int amount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.type = type;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Transaction getnexTransaction() {
        return nextTransaction;
    }

    public void setNextTransaction(Transaction nextTransaction) {
        this.nextTransaction = nextTransaction;
    }

    public String displayTransaction() {
        return "To " + recipient.getName() + "(id = " + recipient.getId() + ") "
                + (type == TransactionType.DEBIT ? "-" : "+") + amount
                + " with id = " + id;
    }
}

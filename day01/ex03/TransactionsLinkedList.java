package day01.ex03;

import java.util.UUID;

public class TransactionsLinkedList {
    private Transaction head;

    public TransactionsLinkedList() {
        this.head = null;
    }

    public void addTransaction(Transaction transaction) {
        if (this.head == null) {
            this.head = transaction; // First transaction in the list
        } else {
            transaction.setNextTransaction(head); // New transaction points to the current head
            this.head = transaction; // Update the head to the new transaction
        }
    }

    public void removeTrandactionById(UUID id) throws TransactionNotFoundException {
        if (head == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }
        if (head.getId().equals(id)) {
            head = head.getnexTransaction();
            return;
        }

        Transaction current = head;
        Transaction previouse = null;

        while (current != null && !current.getId().equals(id)) {
            previouse = current;
            current = current.getnexTransaction();
        }
        if (current == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }

        previouse.setNextTransaction(current.getnexTransaction());

    }

    public Transaction[] toArray() {
        int size = 0;
        Transaction current = head;

        // Count the number of transactions
        while (current != null) {
            size++;
            current = current.getnexTransaction();
        }

        Transaction[] transactionsArray = new Transaction[size];
        current = head;
        int index = 0;

        // Copy transactions to array
        while (current != null) {
            transactionsArray[index++] = current;
            current = current.getnexTransaction();
        }

        return transactionsArray;
    }
}

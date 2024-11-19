package day01.ex04;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Transaction head;

    @Override
    public void addTransaction(Transaction transaction) {
        if (head == null) {
            head = transaction;
        } else {
            transaction.setNextTransaction(head);
            head = transaction;
        }
    }

    @Override
    public void removeTransactionById(UUID id) throws TransactionNotFoundException {
        if (head == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }
        if (head.getId().equals(id)) {
            head = head.getnexTransaction();
            return;
        }

        Transaction current = head;
        Transaction previous = null;

        while (current != null && !current.getId().equals(id)) {
            previous = current;
            current = current.getnexTransaction();
        }
        if (current == null) {
            throw new TransactionNotFoundException("Transaction with ID " + id + " not found.");
        }

        previous.setNextTransaction(current.getnexTransaction());
    }

    @Override
    public Transaction[] toArray() {
        int size = 0;
        Transaction current = head;
        while (current != null) {
            size++;
            current = current.getnexTransaction();
        }

        Transaction[] transactionsArray = new Transaction[size];
        current = head;
        for (int i = 0; i < size; i++) {
            transactionsArray[i] = current;
            current = current.getnexTransaction();
        }
        return transactionsArray;
    }
}

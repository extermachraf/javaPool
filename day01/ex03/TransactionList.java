package day01.ex03;

import java.util.UUID;

public interface TransactionList {
    public void addTransaction(Transaction transaction);
    public void removeTransactionById(UUID id) throws TransactionNotFoundException;
    public Transaction[] toArray();
}

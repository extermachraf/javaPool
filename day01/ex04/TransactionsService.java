package day01.ex04;

import java.util.UUID;

public class TransactionsService {
    private UsersList usersList;

    public TransactionsService() {
        usersList = new UsersArrayList();
    }

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int getUserBalance(int userId) throws UserNotFoundException {
        User user = usersList.retrieveUserByID(userId);
        return user.getBalance();
    }

    public void performTransfer(int senderId, int recipientId, int amount)
            throws UserNotFoundException, IllegalTransactionException {
        User sender = usersList.retrieveUserByID(senderId);
        User recipient = usersList.retrieveUserByID(recipientId);

        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Insufficient balance for transfer.");
        }

        Transaction debitTransaction = new Transaction(UUID.randomUUID(), sender, recipient, TransactionType.DEBIT,
                amount);
        sender.getTransactionsList().addTransaction(debitTransaction);

        Transaction creditTransaction = new Transaction(debitTransaction.getId(), recipient, sender,
                TransactionType.CREDIT, amount);
        recipient.getTransactionsList().addTransaction(creditTransaction);

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

    public Transaction[] getUserTransactions(int userId) throws UserNotFoundException {
        User user = usersList.retrieveUserByID(userId);
        return user.getTransactionsList().toArray();
    }

    public void removeTransaction(int userId, UUID transactionId)
            throws UserNotFoundException, TransactionNotFoundException {
        User user = usersList.retrieveUserByID(userId);
        user.getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] checkUnpairedTransactions() {
        TransactionsList unpairedTransactions = new TransactionsLinkedList();
        for (int i = 0; i < usersList.getNumberOfUsers(); i++) {
            User user = usersList.retrieveUserByIndex(i);
            Transaction[] userTransactions = user.getTransactionsList().toArray();

            for (Transaction transaction : userTransactions) {
                if (!isPairedTransaction(transaction)) {
                    unpairedTransactions.addTransaction(transaction);
                }
            }
        }
        return unpairedTransactions.toArray();
    }

    private boolean isPairedTransaction(Transaction transaction) {
        TransactionType oppositeType = transaction.getType() == TransactionType.DEBIT ? TransactionType.CREDIT
                : TransactionType.DEBIT;
        Transaction[] recipientTransactions = transaction.getRecipient().getTransactionsList().toArray();

        for (Transaction recipientTransaction : recipientTransactions) {
            if (recipientTransaction.getId().equals(transaction.getId()) &&
                    recipientTransaction.getType() == oppositeType) {
                return true;
            }
        }
        return false;
    }
}

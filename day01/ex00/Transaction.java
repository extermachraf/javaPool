
/**
 * The Transaction class represents a financial transaction between two users.
 * Each transaction has a unique identifier, a sender, a recipient, a category (either "credit" or "debit"),
 * and an amount. The class ensures that the transaction is valid by checking the category and the amount.
 * If the category is "debit", it also checks that the sender has sufficient balance to cover the transaction.
 * The class provides getter methods for all fields and a setter method for the amount.
 * It also includes a display method to print the transaction details.
 * 
 * Fields:
 * - Id: A unique identifier for the transaction.
 * - Sender: The user who is sending the amount.
 * - Recipient: The user who is receiving the amount.
 * - Category: The category of the transaction, either "credit" or "debit".
 * - Amount: The amount of the transaction.
 * 
 * Methods:
 * - Transaction(User sender, User recipient, String category, double amount): Constructor to create a new transaction.
 * - getId(): Returns the unique identifier of the transaction.
 * - getSender(): Returns the sender of the transaction.
 * - getRecipient(): Returns the recipient of the transaction.
 * - getCategory(): Returns the category of the transaction.
 * - getAmount(): Returns the amount of the transaction.
 * - setAmount(double amount): Sets the amount of the transaction and updates the balances of the sender and recipient.
 * - display(): Prints the details of the transaction.
 */
package day01.ex00;

import java.util.UUID;

public class Transaction {
    private String Id;
    private User Sender;
    private User Recipient;
    private String Category;
    private double Amount;

    public Transaction(User sender, User recipient, String category, double amount) {
        if (!(category.equals("credit") || category.equals("debit"))) {
            System.err.println("\"Category must be either 'credit' or 'debit'\"");
            System.exit(-1);
        }
        this.Id = UUID.randomUUID().toString();
        this.Sender = sender;
        this.Recipient = recipient;
        this.Category = category;
        setAmount(amount);
    }

    // geters
    public String getId() {
        return this.Id;
    }

    public User getSender() {
        return this.Sender;
    }

    public User getRecipient() {
        return this.Recipient;
    }

    public String getCategory() {
        return this.Category;
    }

    public double getAmount() {
        return this.Amount;
    }

    // seters
    public void setAmount(double amount) {
        if (amount < 0) {
            System.err.println("the amount can't be negative");
            System.exit(-1);
        }
        this.Amount = amount;
        if (this.Category.equals("debit")) {
            if (this.Sender.getBalance() < amount) {
                System.err.println("invalid transaction");
                System.exit(-1);
            }
            this.Sender.setBalance(this.Sender.getBalance() - this.Amount);
            this.Recipient.setBalance(this.Recipient.getBalance() + this.Amount);

        } else if (this.Category.equals("credit"))
            this.Recipient.setBalance(this.Recipient.getBalance() + amount);
    }

    public void display() {
        System.out.println(
                "Transaction{id='" + this.Id + "', sender=" + this.Sender.getName() + ", recipient="
                        + this.Recipient.getName() +
                        ", category='" + this.Category + "', amount=" + this.Amount + "}");
    }
}
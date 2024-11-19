
/**
 * The User class represents a user with a unique ID, name, and balance.
 * The ID is generated automatically using the UserIdsGenerator.
 * The balance cannot be negative.
 * 
 * Methods:
 * - getId(): Returns the user's ID.
 * - getName(): Returns the user's name.
 * - getBalance(): Returns the user's balance.
 * - setName(String name): Sets the user's name.
 * - setBalance(double balance): Sets the user's balance. If the balance is negative, an error message is printed and the program exits.
 * - display(): Prints the user's details to the console.
 */

package day01.ex02;

import day01.ex01.UserIdsGenerator;

public class User {
    private final int Id;
    private String Name;
    private double Balance;

    public User(String name, double balance) {
        this.Id = UserIdsGenerator.getInstance().generateId();
        setName(name);
        setBalance(balance);
    }

    // geters
    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public double getBalance() {
        return Balance;
    }

    // seters

    public void setName(String name) {
        this.Name = name;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            System.err.println("Balance can't be negative");
            System.exit(-1);
        }
        this.Balance = balance;
    }

    // methods
    public void display() {
        System.out.println("User: " + Name + " with id: " + Id + " has balance: " + Balance);
    }

}
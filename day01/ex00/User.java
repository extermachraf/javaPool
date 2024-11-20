
/**
 * The User class represents a user with an ID, name, and balance.
 * It provides methods to get and set these properties, as well as a method to display user information.
 * The balance cannot be set to a negative value.
 * 
 * Attributes:
 * - Id: The unique identifier for the user.
 * - Name: The name of the user.
 * - Balance: The balance of the user, which must be non-negative.
 * 
 * Methods:
 * - getId(): Returns the user's ID.
 * - getName(): Returns the user's name.
 * - getBalance(): Returns the user's balance.
 * - setId(int id): Sets the user's ID.
 * - setName(String name): Sets the user's name.
 * - setBalance(double balance): Sets the user's balance, ensuring it is non-negative.
 * - display(): Displays the user's information.
 * 
 * Constructor:
 * - User(int id, String name, double balance): Initializes a new user with the specified ID, name, and balance.
 */
package day01.ex00;

public class User {
    private int Id;
    private String Name;
    private double Balance;

    public User(int id, String name, double balance) {
        setId(id);
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
    public void setId(int id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setBalance(double balance) throws IllegalArgumentException {
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
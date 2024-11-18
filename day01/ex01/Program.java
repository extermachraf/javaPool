package day01.ex01;

public class Program {
    public static void main(String[] args) {
        // Create users
        User user1 = new User("Alice", 500.0);
        User user2 = new User("Bob", 1500.0);
        User user3 = new User("Charlie", 250.0);

        // Display user info
        user1.display();
        user2.display();
        user3.display();
    }
}
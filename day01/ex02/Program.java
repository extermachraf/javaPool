package day01.ex02;

public class Program {
    public static void main(String[] args) {
        // Create an instance of UsersArrayList
        UsersList userList = new UsersArrayList();

        // Create and add users
        User user1 = new User("Alice", 500.0);
        User user2 = new User("Bob", 1500.0);
        User user3 = new User("Charlie", 250.0);

        userList.addUser(user1);
        userList.addUser(user2);
        userList.addUser(user3);

        // Display the users
        try {
            System.out.println("Retrieving user by ID:");
            User retrievedUser = userList.retrieveUserByID(2); // Bob has ID 2
            retrievedUser.display();

            System.out.println("\nRetrieving user by index:");
            retrievedUser = userList.retrieveUserByIndex(6); // First user (Alice)
            retrievedUser.display();

            System.out.println("\nTotal number of users: " + userList.getNumberOfUsers());
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}

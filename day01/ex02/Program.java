package day01.ex02;

public class Program {
    public static void main(String[] args) {
        UsersArrayList userList = new UsersArrayList();

        System.out.println("Initial capacity: " + userList.getCapacity());

        // Add 12 users to trigger array expansion
        for (int i = 0; i < 12; i++) {
            User user = new User("User" + (i + 1), (i + 1) * 100);
            userList.addUser(user);
            System.out.println("Added user: " + user.getName() +
                    ", ID: " + user.getId() +
                    ", Current count: " + userList.getNumberOfUsers() +
                    ", Current capacity: " + userList.getCapacity());
        }

        // Test retrieval and expansion
        try {
            System.out.println("\n=== Stress Test ===");
            // Should be in expanded array (index 10)
            User user10 = userList.retrieveUserByIndex(10);
            System.out.println("User at index 10: " + user10.getName() + " (ID: " + user10.getId() + ")");

            // Should have ID 12 (last added user)
            User lastUser = userList.retrieveUserByID(12);
            System.out.println("User with ID 12: " + lastUser.getName());

            // Test non-existent ID
            try {
                userList.retrieveUserByID(999);
            } catch (UserNotFoundException e) {
                System.out.println("\nExpected error: " + e.getMessage());
            }
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("\nFinal statistics:");
        System.out.println("Total users: " + userList.getNumberOfUsers());
        System.out.println("Array capacity: " + userList.getCapacity());
    }
}
package day01.ex05;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import day01.ex04.*;

public class Menu {
    final String RED = "\u001B[31m";
    final String RESET = "\u001B[0m";
    private TransactionsService transactionsService;
    private boolean isDevMode;

    public Menu(TransactionsService transactionsService, boolean isDevMode) {
        this.transactionsService = transactionsService;
        this.isDevMode = isDevMode;
    }

    public void displayMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (isDevMode) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
        }
        System.out.println("7. Finish execution");
    }

    public void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("-----------------------------------------------");
            displayMenu();
            System.out.print("-> ");
            while (true) {
                try {
                    String input = scanner.nextLine();
                    // check if input is empty
                    if (input.trim().isEmpty()) {
                        throw new IllegalArgumentException("Input cannot be empty. Please enter a number.");
                    }
                    if (!input.matches("\\d+")) {
                        throw new IllegalArgumentException("Invalid input. Please enter a number.");
                    }
                    int choice = Integer.parseInt(input);
                    // Consume the newline
                    switch (choice) {
                        case 1 -> addUser(scanner);
                        case 2 -> viewUserBalance(scanner);
                        case 3 -> performTransfer(scanner);
                        case 4 -> viewUserTransactions(scanner);
                        case 5 -> {
                            if (isDevMode)
                                removeTransaction(scanner);
                            else
                                System.out.println(RED + "Invalid option in production mode." + RESET);
                        }
                        case 6 -> {
                            if (isDevMode)
                                checkTransferValidity();
                            else
                                System.out.println(RED + "Invalid option in production mode." + RESET);
                        }
                        case 7 -> {
                            exit = true;
                            System.out.println("\u001B[32mExiting the application.\u001B[0m");
                            break;
                        }
                        default ->
                            System.out.println(
                                    RED + "Invalid choice. Please enter a valid number from the menu." + RESET);
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(RED + "Error: Invalid input type. Please enter a number." + RESET);
                    break;
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(RED + "Error: " + e.getMessage() + RESET);
                    break;
                } catch (Exception e) {
                    System.out.println(RED + "Unexpected error occurred: " + e.getMessage() + RESET);
                    break;
                }
            }
        }
    }

    private void addUser(Scanner scanner) {
        while (true) { // Loop until valid input is provided
            try {
                System.out.println("Enter a user name and a balance");
                System.out.print("-> ");
                String input = scanner.nextLine();

                // Check for empty input
                if (input.trim().isEmpty()) {
                    throw new IllegalArgumentException("Input cannot be empty. Please enter a username and balance.");
                }

                // Split input and validate
                String[] splitInput = input.split(" ");
                if (splitInput.length != 2) {
                    throw new IllegalArgumentException(
                            "Invalid format. Enter the name of the user and their balance as: <Username> <Balance>");
                }

                // Extract and validate individual inputs
                String name = splitInput[0];
                double balance = Double.parseDouble(splitInput[1]);
                if (balance < 0) {
                    throw new IllegalArgumentException("Balance cannot be negative.");
                }

                // Create and add the user
                User newUser = new User(name, balance);
                transactionsService.addUser(newUser);
                System.out.println("\u001B[32mUser with ID = " + newUser.getId() + " is added.\u001B[0m");
                break; // Exit loop when input is valid

            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m"); // RED color for error
            }
        }
    }

    private void viewUserBalance(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter a user ID");
                System.out.print("-> ");
                String input = scanner.nextLine();
                // check if the string is empty

                if (input.trim().isEmpty()) {
                    throw new IllegalArgumentException("User ID cannot be empty.");
                }
                // check the string if its a number
                if (!input.matches("\\d+")) {
                    throw new IllegalArgumentException("User ID must be a valid number.");
                }
                int userId = Integer.parseInt(input);

                User user = transactionsService.getUserList().retrieveUserByID(userId);
                System.out.println("\u001B[32m" + user.getName() + " - " + user.getBalance() + "\u001B[0m");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(RED + e.getMessage() + RESET);
                break;
            } catch (UserNotFoundException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            }
        }
    }

    private void performTransfer(Scanner scanner) {
        while (true) {

            try {
                System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
                System.out.print("-> ");
                String input = scanner.nextLine();
                // check if string is empty
                if (input.trim().isEmpty()) {
                    throw new IllegalArgumentException("Input cannot be empty.");
                }
                // split the input
                String[] splitInput = input.split(" ");
                if (splitInput.length != 3) {
                    throw new IllegalArgumentException(
                            "Invalid format. Enter the sender ID, recipient ID, and transfer amount as: <Sender ID> <Recipient ID> <Amount>");
                }
                // check if the inputs is a numbers
                if (!splitInput[0].matches("\\d+") || !splitInput[1].matches("\\d+")
                        || !splitInput[2].matches("\\d+")) {
                    throw new IllegalArgumentException("User ID and transfer amount must be valid numbers.");
                }
                // parse the inputs
                int senderId = Integer.parseInt(splitInput[0]);
                int recipientId = Integer.parseInt(splitInput[1]);
                int amount = Integer.parseInt(splitInput[2]);
                if (amount <= 0) {
                    throw new IllegalArgumentException("Transfer amount must be greater than zero.");
                }

                transactionsService.performTransfer(senderId, recipientId, amount);
                System.out.println("\u001B[32mTransfer of " + amount + " from user ID = " + senderId + " to user ID = "
                        + recipientId
                        + " completed.\u001B[0m");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break; // Clear invalid input
            } catch (UserNotFoundException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            } catch (IllegalTransactionException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            }
        }
    }

    private void viewUserTransactions(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter a user ID");
                System.out.print("-> ");
                String input = scanner.nextLine();
                // check if the string is empty
                if (input.trim().isEmpty()) {
                    throw new IllegalArgumentException("User ID cannot be empty.");
                }
                // check if the string is a number
                if (!input.matches("\\d+")) {
                    throw new IllegalArgumentException("User ID must be a valid number.");
                }
                int userId = Integer.parseInt(input);

                User user = transactionsService.getUserList().retrieveUserByID(userId);
                Transaction[] transactions = user.getTransactionsList().toArray();
                if (transactions.length == 0) {
                    System.out.println("No transactions found for this user.");
                } else {
                    for (Transaction transaction : transactions) {
                        System.out.println("\u001B[32m" + transaction.displayTransaction() + "\u001B[0m");
                    }
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            } catch (UserNotFoundException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            }

        }
    }

    private void removeTransaction(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter a user ID and a transfer ID");
                System.out.print("-> ");
                String inpuS = scanner.nextLine();
                // check if the string is empty
                if (inpuS.trim().isEmpty()) {
                    throw new IllegalArgumentException("Input cannot be empty.");
                }
                // split the input
                String[] splitInput = inpuS.split(" ");
                if (splitInput.length != 2) {
                    throw new IllegalArgumentException(
                            "Invalid format. Enter the user ID and transfer ID as: <User ID> <Transfer ID>");
                }
                // check if the inputs are numbers
                if (!splitInput[0].matches("\\d+")) {
                    throw new IllegalArgumentException("User ID must be a valid number.");
                }
                // parse the inputs
                int userId = Integer.parseInt(splitInput[0]);
                String transactionIdInput = splitInput[1];
                UUID transactionId = UUID.fromString(transactionIdInput);

                transactionsService.removeTransaction(userId, transactionId);
                System.out.println("\u001B[32mTransfer with ID = " + transactionId + " removed.\u001B[0m");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(RED + " Error" + e.getMessage() + RESET);
                break;
            } catch (UserNotFoundException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            } catch (TransactionNotFoundException e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                break;
            }
        }
    }

    private void checkTransferValidity() {
        try {
            Transaction[] invalidTransactions = transactionsService.checkUnpairedTransactions();
            if (invalidTransactions.length == 0) {
                System.out.println("All transactions are valid.");
            } else {
                System.out.println("Check results:");
                for (Transaction transaction : invalidTransactions) {
                    User sender = transaction.getSender();
                    User recipient = transaction.getRecipient();
                    System.out.println("\u001B[32m" + recipient.getName() + " (id = " + recipient.getId() +
                            ") has an unacknowledged transfer id = " + transaction.getId() +
                            " from " + sender.getName() + " (id = " + sender.getId() +
                            ") for " + transaction.getAmount() + "\u001B[0m");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

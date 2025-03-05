package fr._42;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final AtomicBoolean running = new AtomicBoolean(true);

    public static void main(String[] args) {
        try (
                Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Read initial server greeting
            String welcomeMessage = serverInput.readLine();
            System.out.println("Server: " + welcomeMessage);

            // Handle authentication first
            boolean authenticated = handleAuthentication(serverOutput, serverInput, userInput);

            if (authenticated) {
                // Start a separate thread to listen for messages from the server
                Thread messageListener = createMessageListenerThread(serverInput);
                messageListener.start();

                // Handle messaging in the main thread
                handleChatSession(serverOutput, userInput);

                // Wait for the listener thread to finish when exiting
                running.set(false);
                messageListener.join();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the authentication process (sign in or sign up)
     */
    private static boolean handleAuthentication(PrintWriter serverOutput, BufferedReader serverInput, BufferedReader userInput)
            throws IOException {
        while (true) {
            // Receive command prompt from server
            String authPrompt = serverInput.readLine();
            System.out.println("Server: " + authPrompt);

            // Get user input for authentication command
            System.out.print("Enter command: ");
            String authCommand = userInput.readLine().trim();
            serverOutput.println(authCommand);

            if ("signUp".equalsIgnoreCase(authCommand) || "signIn".equalsIgnoreCase(authCommand)) {
                // Username prompt
                String usernamePrompt = serverInput.readLine();
                System.out.println("Server: " + usernamePrompt);
                System.out.print("Username: ");
                String username = userInput.readLine();
                serverOutput.println(username);

                // Password prompt
                String passwordPrompt = serverInput.readLine();
                System.out.println("Server: " + passwordPrompt);
                System.out.print("Password: ");
                String password = userInput.readLine();
                serverOutput.println(password);

                // Get authentication result
                String result = serverInput.readLine();
                System.out.println("Server: " + result);

                // Check if authentication was successful (server should send a message indicating start of chat)
                if (result.contains("Start messaging")) {
                    return true;
                }
                // If authentication failed, the server will send the command prompt again
            } else {
                // Invalid command
                String response = serverInput.readLine();
                System.out.println("Server: " + response);
            }
        }
    }

    /**
     * Creates a thread that listens for incoming messages from the server
     */
    private static Thread createMessageListenerThread(BufferedReader serverInput) {
        return new Thread(() -> {
            try {
                String message;
                while (running.get() && (message = serverInput.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                if (running.get()) {
                    System.err.println("Connection to server lost: " + e.getMessage());
                }
            }
        });
    }

    /**
     * Handles the chat session after successful authentication
     */
    private static void handleChatSession(PrintWriter serverOutput, BufferedReader userInput) throws IOException {
        String userMessage;
        System.out.println("Chat session started. Type 'exit' to quit.");

        while (true) {
            userMessage = userInput.readLine();
            if (userMessage == null || "exit".equalsIgnoreCase(userMessage.trim())) {
                serverOutput.println("exit");
                System.out.println("Exiting chat...");
                break;
            }
            serverOutput.println(userMessage);
        }
    }
}
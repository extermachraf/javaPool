package fr._42;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
                // Establish connection to server
                Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                // Writer to send data to server
                PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream(), true);
                // Reader to receive data from server
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // Reader to get user input from console
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String serverResponse;
            // Listen for server messages
            while ((serverResponse = serverInput.readLine()) != null) {
                System.out.println("Server: " + serverResponse);

                // Start command handling after initial server greeting
                if (serverResponse.equals("Hello from Server!")) {
                    processUserCommands(serverOutput, serverInput, userInput);
                    break; // Exit after command processing
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles user command input and server communication
     */
    private static void processUserCommands(PrintWriter serverOutput, BufferedReader serverInput, BufferedReader userInput)
            throws IOException {
        while (true) {
            System.out.print("Enter command (signUp, signIn, exit): ");
            String userCommand = userInput.readLine().trim();
            serverOutput.println(userCommand);  // Send command to server

            if ("exit".equalsIgnoreCase(userCommand)) {
                handleExitProtocol(serverOutput, serverInput);
                break;
            } else if ("signUp".equalsIgnoreCase(userCommand) || "signIn".equalsIgnoreCase(userCommand)) {
                handleAuthenticationFlow(serverOutput, serverInput, userInput, userCommand);
            } else {
                // Handle simple commands
                String response = serverInput.readLine();
                System.out.println("Server: " + response);
                if(response.equals("Successful!") || response.equals("Failed!")){
                    break;
                }
            }
        }
    }

    /**
     * Handles authentication process (both signup and signin)
     */
    private static void handleAuthenticationFlow(PrintWriter serverOutput, BufferedReader serverInput,
                                                 BufferedReader userInput, String authType) throws IOException {
        // Get username
        System.out.println(serverInput.readLine());  // Show server prompt
        String username = userInput.readLine();
        serverOutput.println(username);

        // Get password
        System.out.println(serverInput.readLine());  // Show server prompt
        String password = userInput.readLine();
        serverOutput.println(password);

        // Get final result
        String authResult = serverInput.readLine();
        System.out.println("Authentication Result: " + authResult);
    }

    /**
     * Handles graceful exit procedure
     */
    private static void handleExitProtocol(PrintWriter serverOutput, BufferedReader serverInput) throws IOException {
        serverOutput.println("exit");  // Confirm exit command
        String exitMessage = serverInput.readLine();
        System.out.println("Server: " + exitMessage);
    }
}
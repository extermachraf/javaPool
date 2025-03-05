package fr._42.Server;

import fr._42.repositpries.UserRepoExeption;
import fr._42.services.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Server {
    private final int serverPort;

    public Server(int port) {
        this.serverPort = port;
    }

    /**
     * Starts the server and listens for incoming connections
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Server started on port " + serverPort);
            while (true) {
                Socket clientConnection = serverSocket.accept();
                System.out.println("New client connected: " + clientConnection.getInetAddress());
                // Handle each client in a separate thread
                new Thread(() -> handleClientSession(clientConnection)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    /**
     * Handles communication with a connected client
     */
    private void handleClientSession(Socket clientConnection) {
        try (
                PrintWriter clientOutput = new PrintWriter(clientConnection.getOutputStream(), true);
                BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()))
        ) {
            // Send initial greeting
            clientOutput.println("Hello from Server!");

            String clientCommand;
            while ((clientCommand = clientInput.readLine()) != null) {
                // Create new application context for each request
                try (AnnotationConfigApplicationContext context =
                             new AnnotationConfigApplicationContext("fr._42.config")) {
                    UsersService userManager = context.getBean(UsersService.class);

                    switch (clientCommand.toLowerCase()) {
                        case "signup":
                            handleRegistration(clientOutput, clientInput, userManager);
                            break;
                        case "signin":
                            handleLogin(clientOutput, clientInput, userManager);
                            break;
                        case "exit":
                            clientOutput.println("Closing connection");
                            return;  // End session
                        default:
                            clientOutput.println("Invalid command");
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Client handling error: " + e.getMessage());
        } finally {
            try {
                clientConnection.close();
            } catch (IOException e) {
                System.err.println("Error closing client connection: " + e.getMessage());
            }
        }
    }

    /**
     * Handles user registration process
     */
    private void handleRegistration(PrintWriter clientOutput, BufferedReader clientInput, UsersService userManager)
            throws IOException {
        clientOutput.println("Enter username:");
        String newUsername = clientInput.readLine();
        clientOutput.println("Enter password:");
        String newPassword = clientInput.readLine();

        try {
            userManager.signUp(newUsername, newPassword);
            clientOutput.println("Successful!");
        } catch (UserRepoExeption e) {
            clientOutput.println(e.getMessage());
        }
    }

    /**
     * Handles user login process
     */
    private void handleLogin(PrintWriter clientOutput, BufferedReader clientInput, UsersService userManager)
            throws IOException {
        clientOutput.println("Enter username:");
        String existingUsername = clientInput.readLine();
        clientOutput.println("Enter password:");
        String existingPassword = clientInput.readLine();

        try{
            Optional<fr._42.models.User> authenticatedUser = userManager.signIn(existingUsername, existingPassword);
            clientOutput.println(authenticatedUser.isPresent() ? "Successful!" : "Failed!");
        } catch (UserRepoExeption e){
            clientOutput.println(e.getMessage());
        }
    }
}
package fr._42.Server;

import fr._42.models.Message;
import fr._42.models.User;
import fr._42.repositpries.message.MessageRepoExeption;
import fr._42.repositpries.message.MessageRepository;
import fr._42.repositpries.user.UserRepoExeption;
import fr._42.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Server {
    private final int serverPort = 12345;
    private static final List<ClientHandler> activeClients = new CopyOnWriteArrayList<>();
    private final UsersService usersService;
    private final MessageRepository messageRepository;

    @Autowired
    public Server(UsersService usersService, MessageRepository messageRepository) {
        this.usersService = usersService;
        this.messageRepository = messageRepository;
    }

    private static class ClientHandler {
        private final User user;
        private final PrintWriter writer;
        private final Socket socket;

        public ClientHandler(User user, PrintWriter writer, Socket socket) {
            this.user = user;
            this.writer = writer;
            this.socket = socket;
        }

        public void sendMessage(String message) {
            if (!socket.isClosed()) {
                writer.println(message);
            }
        }

        public boolean isConnected() {
            return !socket.isClosed();
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Server started on port " + serverPort);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server startup error: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            writer.println("Hello from Server!");
            User authenticatedUser = authenticateUser(writer, reader);

            if (authenticatedUser != null) {
                handleChatSession(authenticatedUser, writer, reader, clientSocket);
            }

        } catch (IOException e) {
            sendErrorMessage(writer, "Connection error. Please try again later.");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private User authenticateUser(PrintWriter writer, BufferedReader reader) throws IOException {
        while (true) {
            try {
                writer.println("Enter command (signUp/signIn) or exit:");
                String command = reader.readLine();

                if (command == null) return null;

                if ("signIn".equalsIgnoreCase(command)) {
                    return handleSignIn(writer, reader);
                } else if ("signUp".equalsIgnoreCase(command)) {
                    return handleSignUp(writer, reader);
                }else if("exit".equalsIgnoreCase(command)){
                    writer.println("GoodBye!");
                    return null;
                } else {
                    writer.println("Invalid command. Please use 'signUp' or 'signIn'");
                }
            } catch (UserRepoExeption e) {
                sendErrorMessage(writer, "Authentication error: " + e.getMessage());
            }
        }
    }

    private User handleSignIn(PrintWriter writer, BufferedReader reader) throws IOException, UserRepoExeption {
        try {
            writer.println("Enter username:");
            String username = reader.readLine();
            writer.println("Enter password:");
            String password = reader.readLine();

            Optional<User> user = usersService.signIn(username, password);
            if (user.isEmpty()) {
                sendErrorMessage(writer, "Invalid username or password");
                return null;
            }
            return user.get();
        } catch (UserRepoExeption e) {
            sendErrorMessage(writer, "Login failed: " + e.getMessage());
            throw e;
        }
    }

    private User handleSignUp(PrintWriter writer, BufferedReader reader) throws IOException {
        try {
            writer.println("Enter username:");
            String username = reader.readLine();
            writer.println("Enter password:");
            String password = reader.readLine();

            usersService.signUp(username, password);
            return usersService.signIn(username, password).orElse(null);
        } catch (Exception e) {
            sendErrorMessage(writer, "Registration failed: " + e.getMessage());
            return null;
        }
    }

    private void handleChatSession(User user, PrintWriter writer, BufferedReader reader, Socket socket) {
        ClientHandler handler = new ClientHandler(user, writer, socket);
        activeClients.add(handler);
        writer.println("Start messaging (type 'exit' to quit)");

        try {
            String message;
            while ((message = reader.readLine()) != null ) {
                if(message.isEmpty()) continue;
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                try {
                    Message chatMessage = new Message();
                    chatMessage.setSender(user);
                    chatMessage.setText(message);
                    chatMessage.setTimestamp(LocalDateTime.now());
                    messageRepository.save(chatMessage);

                    String formattedMessage = String.format("[%s] %s: %s",
                            chatMessage.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_TIME),
                            user.getFullName(),
                            message);

                    broadcastMessage(formattedMessage);
                } catch (MessageRepoExeption e) {
                    sendErrorMessage(writer, "Error sending message: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Client communication error: " + e.getMessage());
        } finally {
            activeClients.remove(handler);
            broadcastMessage(user.getFullName() + " has left the chat");
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : activeClients) {
            try {
                if (client.isConnected()) {
                    client.sendMessage(message);
                }
            } catch (Exception e) {
                activeClients.remove(client);
            }
        }
    }

    private void sendErrorMessage(PrintWriter writer, String message) {
        if (writer != null) {
            writer.println("[ERROR] " + message);
        }
    }
}
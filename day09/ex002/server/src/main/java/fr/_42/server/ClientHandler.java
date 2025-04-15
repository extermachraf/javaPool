package fr._42.server;

import fr._42.models.Room;
import fr._42.models.User;
import fr._42.repositories.messages.MessageRepositoryImpl;
import fr._42.repositories.room.RoomRepository;
import fr._42.repositories.room.RoomRepositoryImpl;
import fr._42.server.states.ConnectionState;
import fr._42.server.states.InitialState;
import fr._42.services.UsersServiceImpl;
import fr._42.server.states.*;
import fr._42.exeptions.ServException;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter @Setter
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final UsersServiceImpl usersService;
    private final MessageRepositoryImpl messageRepository;
    private final RoomRepository roomRepository;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private ConnectionState currentState;
    private User authenticatedUser;
    private Room currentRoom;



    private static final List<ClientHandler> authenticatedUsers = new CopyOnWriteArrayList<>();
    private static final Map<Long, CopyOnWriteArrayList<ClientHandler>> roomUsers  = new ConcurrentHashMap<>();

    public static List<ClientHandler> getAuthenticatedUsers() {
        return authenticatedUsers;
    }
    public static Map<Long, CopyOnWriteArrayList<ClientHandler>> getRoomUsers() {return roomUsers;}

    public ClientHandler(Socket socket, UsersServiceImpl usersService, MessageRepositoryImpl messageRepository, RoomRepository roomRepository) {
        this.clientSocket = socket;
        this.usersService = usersService;
        this.currentState = new InitialState();
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    public void setState(ConnectionState state) {
        this.currentState = state;
    }

    public void stop() {
        running.set(false);
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }

    public void broadcastMessage(String message) throws IOException {
            for (ClientHandler client : authenticatedUsers) {
                if(client != this && client.getAuthenticatedUser() != null) {
                    try{
                        PrintWriter writer = new PrintWriter(client.getClientSocket().getOutputStream(), true);
                        writer.println(message);
                    }catch (IOException e){
                        System.err.println("Error writing " + client.getClientSocket().getRemoteSocketAddress());
                    }
                }
            }
    }

    public void broadcastToRoom(String message) {
        Long roomId = currentRoom.getId();
        List<ClientHandler> roomClients = roomUsers.get(roomId);

        if (roomClients != null) {
            for (ClientHandler client : roomClients) {
                if (client != this && client.getAuthenticatedUser() != null) {
                    try {
                        PrintWriter writer = new PrintWriter(client.getClientSocket().getOutputStream(), true);
                        writer.println(message);
                    } catch (IOException e) {
                        System.err.println("Error writing to " + client.getClientSocket().getRemoteSocketAddress());
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            while (running.get() && !clientSocket.isClosed()) {
                currentState.handle(this, writer, reader);
            }

        } catch (IOException e) {
            throw new ServException("server socket closed...");
        } finally {
            try {
                if (authenticatedUser != null) {
                    authenticatedUsers.remove(this);
                }

                if (currentRoom != null && roomUsers.containsKey(currentRoom.getId())) {
                    roomUsers.get(currentRoom.getId()).remove(this);
                }
                if (!clientSocket.isClosed()) clientSocket.close();
            } catch (IOException e) {
                throw new ServException("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
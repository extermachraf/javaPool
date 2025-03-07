package fr._42.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr._42.dtos.AuthDTO;
import fr._42.dtos.BaseMessage;
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
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Server {
    private final int SERVER_PORT = 4000;
    private final UsersService usersService;
    private final MessageRepository messageRepository;
    ServerSocket serverSocket;

    @Autowired
    public Server(UsersService usersService, MessageRepository messageRepository) {
        this.usersService = usersService;
        this.messageRepository = messageRepository;
        try{
            this.serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is listening on port 4000");
        } catch (IOException e){
            System.out.println("Server could not be started");
            System.exit(-1);
        }
    }



    public void start() {
        System.out.println("Server is listening on port from start function " + SERVER_PORT);
        while (true) {
            try (Socket clientSocket = serverSocket.accept()){
                System.out.println("Client connected");
            } catch (IOException e) {
                System.out.println("Server could not be started");
            }
        }
    }
}
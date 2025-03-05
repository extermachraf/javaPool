package fr._42;
import fr._42.Server.Server;
import fr._42.config.SocketsApplicationConfig;
import fr._42.models.User;
import fr._42.repositpries.UsersRepository;
import fr._42.repositpries.UsersRepositoryImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import  java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        int port = 12345;
        Server server = new Server(port);
        server.start();
    }
}
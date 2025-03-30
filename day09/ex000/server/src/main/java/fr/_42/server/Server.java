package fr._42.server;
import fr._42.exeptions.SerevrExeption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor @Getter
public class Server {
    int port;
    PrintWriter sendToClient;
    BufferedReader receiveFromClient;


    public Server(int port) {
        this.port = port;
    }

    public void StartServer() throws SerevrExeption {
        try(ServerSocket serverSocket = new ServerSocket(this.port)){
            System.out.println("Server started on port " + this.port);
            while (true){
                Socket clientSocket = serverSocket.accept();
                this.sendToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                this.receiveFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                sendToClient.println("Hellow from server");
            }
        } catch (IOException e) {
            throw new SerevrExeption("Error starting server" + e.getMessage());
        }
    }
}

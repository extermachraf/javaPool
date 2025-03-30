package client;

import exeptions.ClientExeption;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.*;
import java.net.Socket;

@AllArgsConstructor @Getter
public class Client {
    int port;
    PrintWriter sendToServer;
    BufferedReader receiveFromServer;

    public Client(int port) {
        this.port = port;
    }

    public void StartClient() throws ClientExeption {
        try(Socket clientSocket = new Socket("localhost", port)) {
            this.sendToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            this.receiveFromServer = new BufferedReader(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

            String messageFomServer = receiveFromServer.readLine();
            System.out.println(messageFomServer);
        }catch (IOException e){
            throw new ClientExeption(e.getMessage());
        }
    }
}

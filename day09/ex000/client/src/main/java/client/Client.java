package client;

import exeptions.ClientExeption;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.*;
import java.net.Socket;

@AllArgsConstructor @Getter
public class Client {
    int port;
    BufferedReader userInput;

    public Client(int port) {
        this.port = port;
        this.userInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void StartClient() throws ClientExeption {
        try(Socket clientSocket = new Socket("localhost", port);
            PrintWriter sendToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader receiveFromServer = new BufferedReader(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));) {

            String messageFomServer = receiveFromServer.readLine();
            System.out.println(messageFomServer);

            while (true) {
                messageFomServer = receiveFromServer.readLine();
                if (messageFomServer.matches("ERROR")){
                    System.err.println("an error occurred");
                    break;
                }else if(messageFomServer.matches("OK")){
                    System.out.println("Successful!");
                    break;
                }else if(messageFomServer.matches("Invalid credentials")){
                    System.err.println("Invalid credentials");
                    break;
                }
                System.out.println(messageFomServer);
                System.out.print("> ");
                sendToServer.println(userInput.readLine());
            }
        }catch (IOException e){
            throw new ClientExeption(e.getMessage());
        }
    }
}

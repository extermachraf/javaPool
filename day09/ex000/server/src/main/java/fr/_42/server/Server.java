package fr._42.server;
import fr._42.exeptions.ServException;
import fr._42.services.UsersServiceImpl;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class Constants {
    public static final String ERROR_MSG = "ERROR";
    public static final String SUCCESS_MSG = "OK";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
}

@Getter
public class Server {
    int port;
    UsersServiceImpl usersServiceImpl;


    public Server(int port, UsersServiceImpl usersServiceImpl) {
        this.port = port;
        this.usersServiceImpl = usersServiceImpl;
    }


    public void StartServer() throws ServException {
        try(ServerSocket serverSocket = new ServerSocket(this.port)){
            System.out.println("Server started on port " + this.port);
            while (true){
               this.handleClientConnection(serverSocket);
            }
        } catch (IOException e) {
            throw new ServException("Error starting server" + e.getMessage());
        }
        catch (NullPointerException e){
            System.out.println("Server stopped");
        }
    }

    private void handleClientConnection(ServerSocket serverSocket){
        try(Socket clientSocket = serverSocket.accept();
        PrintWriter sendToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader receiveFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            sendToClient.println("Hello from server");
            processClientRequest(sendToClient, receiveFromClient);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void processClientRequest(PrintWriter sendToClient, BufferedReader receiveFromClient) throws IOException{
        while (true){
            sendToClient.println("Choose signin/signup");
            String action = receiveFromClient.readLine();
            if(!isValidAction(action)){
                sendToClient.println(Constants.ERROR_MSG);
                break;
            }
            String[] credentials = readCredentials(sendToClient, receiveFromClient);
            boolean isSuccess = this.authHandler(action, credentials[0], credentials[1]);
            sendToClient.println(isSuccess ? Constants.SUCCESS_MSG : Constants.INVALID_CREDENTIALS);
        }
    }

    private String[] readCredentials(PrintWriter sendToClient, BufferedReader receiveFromClient) throws IOException {
        sendToClient.println("Enter your username");
        String username = receiveFromClient.readLine();
        sendToClient.println("Enter your password");
        String password = receiveFromClient.readLine();
        return new String[]{username, password};
    }

    private boolean isValidAction(String action) {
        return "signin".equals(action) || "signup".equals(action);
    }


    private boolean authHandler(String action, String username, String password) {
        if(action.equals("signin")){
            try{
                this.usersServiceImpl.SignIn(username, password);
                return true;
            } catch (ServException e) {
                return false;
            }
        }else if(action.equals("signup")){
            try{
                this.usersServiceImpl.SignUp(username, password);
                return true;
            }catch (ServException e){
                return false;
            }
        }
        return false;
    }
}

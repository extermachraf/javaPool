package fr._42.server;
import fr._42.exeptions.ServException;
import fr._42.repositories.messages.MessageRepositoryImpl;
import fr._42.repositories.room.RoomRepository;
import fr._42.repositories.room.RoomRepositoryImpl;
import fr._42.services.UsersServiceImpl;
import lombok.Getter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


@Getter
public class Server {
    int port;
    UsersServiceImpl usersServiceImpl;
    MessageRepositoryImpl messageRepository;
    RoomRepository roomRepository;
    private ServerSocket serverSocket;


    public Server(int port, UsersServiceImpl usersServiceImpl, MessageRepositoryImpl messageRepository, RoomRepository roomRepository) {
        this.port = port;
        this.usersServiceImpl = usersServiceImpl;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }


    public void StartServer() throws ServException {
        try{
            serverSocket = new ServerSocket(this.port);
            System.out.println("Server started on port " + this.port);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.print("Server shutting down...");
                System.out.println("cleaning resources");
                this.closeAllConnections();
                try {
                    if(serverSocket != null && !serverSocket.isClosed()) {
                        serverSocket.close();
                    }
                }catch (IOException e) {
                    System.out.println("Server cleanup ERROR : " + e.getMessage());
                }
            }));
            this.handleClientConnection(serverSocket);
        } catch (IOException e) {
            throw new ServException("Error starting server" + e.getMessage());
        }
        catch (NullPointerException e){
            System.out.println("Server stopped");
        }
    }

    private void handleClientConnection(ServerSocket serverSocket){
        try{
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket, this.usersServiceImpl, this.messageRepository, this.roomRepository));
                clientThread.start();
            }

        } catch (IOException e){
            if (serverSocket.isClosed()) {
                System.out.println("Server socket closed.");
            } else {
                throw new ServException("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    private void closeAllConnections() {
        for (ClientHandler handler : ClientHandler.getAuthenticatedUsers()) {
            try {
                handler.stop(); // properly set a flag to stop handler loop
                if (!handler.getClientSocket().isClosed()) {
                    handler.getClientSocket().close();
                }
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}

package fr._42.client;

import fr._42.exeptions.ClientExeption;
import lombok.Getter;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class Client implements ServerHandlers.MessageListener {
    private final int port;
    private final BufferedReader userInput;
    private final ServerHandlers serverHandlers;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private boolean inChat = false;


    public Client(int port) {
        this.port = port;
        this.userInput = new BufferedReader(new InputStreamReader(System.in));
        this.serverHandlers = new ServerHandlers();
        this.serverHandlers.setListener(this);
    }

    public void onMessageReceived(String message) {
        if(inChat) {
            System.out.println(message);
            System.out.print("> ");
        }else {
            messageQueue.offer(message);
        }
    }

    private void handleInitialState() throws InterruptedException, IOException {
        System.out.println(messageQueue.take().replace("\\n", "\n"));
    }

    private String readLine(String ui) throws IOException {
        if(ui != null && !ui.isEmpty()) System.out.print(ui);
        String message = userInput.readLine();
        return message.trim();
    }

    private void handleAuthState() throws IOException, InterruptedException {
        System.out.print("> ");
        String response = userInput.readLine().trim();
        serverHandlers.sendMessage(response);
        if(!(response.equals("1") || response.equals("2") || response.equals("3"))) {
            System.out.println(messageQueue.take());
            return;
        }
        if(response.equals("3")){
            System.out.println(messageQueue.take());
            return;
        }
        serverHandlers.sendMessage(this.readLine("username: "));
        serverHandlers.sendMessage(this.readLine("password: "));

        System.out.println(messageQueue.take());
    }

    private void disconnection() {
        System.exit(0);
    }

    private  void handleChatState() throws IOException, InterruptedException {
        System.out.println(messageQueue.take());
        inChat = true;

        Thread sender = new Thread(() -> {
           try {
                while (inChat) {
                    System.out.print("> ");
                    String input = userInput.readLine();
                    if (input == null || input.equalsIgnoreCase("exit")) {
                        inChat = false;
                        serverHandlers.sendMessage("exit");
                        break;
                    }
                    if(!input.isEmpty()) {
                        serverHandlers.sendMessage(input);
                    }
                }
           } catch (IOException e) {
               System.out.println("Connection lost");
           }
        });

        sender.start();
        try {
            sender.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void startClient() throws ClientExeption {
        try {
            this.serverHandlers.connect("localhost", port);
            System.out.println("Connected to server.");
            while (true) {
                String msg = messageQueue.take();
                switch (msg) {
                    case "InitialState":
                        handleInitialState();
                        break;
                    case "AuthState":
                        handleAuthState();
                        break;
                    case "chatState":
                        handleChatState();
                        break;
                    case "Terminated":
                        System.out.println("Connection terminated.");
                        disconnection();
                    default:
                        System.out.println(msg);
                        break;
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new ClientExeption("Client error: " + e.getMessage());
        }
    }
}
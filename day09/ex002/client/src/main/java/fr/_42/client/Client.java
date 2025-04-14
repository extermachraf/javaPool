package fr._42.client;

import fr._42.exeptions.ClientExeption;
import fr._42.jsonMessages.Instruction;
import fr._42.jsonMessages.MessageSerialization;
import fr._42.json.Room;
import fr._42.json.SerializeDeserializeMessage;
import lombok.Getter;
import lombok.NonNull;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class Client implements ServerHandlers.MessageListener {
    private final int port;
    private final BufferedReader userInput;
    private final ServerHandlers serverHandlers;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private boolean inChat = false;
    private boolean inRoomState = false;


    public Client(int port) {
        this.port = port;
        this.userInput = new BufferedReader(new InputStreamReader(System.in));
        this.serverHandlers = new ServerHandlers();
        this.serverHandlers.setListener(this);
    }

    public void onMessageReceived(String message) {
        if(inChat) {
            try {
                // First try to deserialize as MessageSerialization (chat messages)
                MessageSerialization chatMessage = SerializeDeserializeMessage.objectMapper.readValue(
                        message, MessageSerialization.class);
                if (chatMessage != null && "CHAT_MESSAGE".equals(chatMessage.getType())) {
                    // Format and display chat message without prompt for history messages
                    System.out.println(String.format("%s : %s", chatMessage.getUsername(), chatMessage.getMessage()));
                    return;
                }
            } catch (Exception ignored) {
                // Not a MessageSerialization, try next format
            }

            try {
                // Try as Instruction (system messages)
                Instruction instruction = SerializeDeserializeMessage.objectMapper.readValue(
                        message, Instruction.class);
                if (instruction != null) {
                    String msg = instruction.getMessage();
                    if ("Terminated".equals(msg)) {
                        inChat = false;
                        System.out.println("Chat session ended.");
                        return;
                    } else if ("Exiting chat...".equals(msg)) {
                        inChat = false;
                        System.out.println(msg);
                        return;
                    }
                    // Print system messages (including history markers)
                    System.out.println(msg);
                }
            } catch (Exception ignored) {
                // If all parsing fails, print raw message
                System.out.println("System: " + message);
            }
            // Only show prompt after non-history messages
            if (!message.contains("Last") && !message.contains("---")) {
                System.out.print("> ");
            }
        } else {
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

    private void handleChatState() throws IOException, InterruptedException {
        // Get and display welcome message
        Instruction welcomeMsg = SerializeDeserializeMessage.deserialize(messageQueue.take(), Instruction.class);
        if (welcomeMsg != null) {
            System.out.println(welcomeMsg.getMessage());
        }
        inChat = true;
        System.out.println("\n\n");

        Thread sender = new Thread(() -> {
            try {
                while (inChat) {
                    System.out.print("> ");
                    String input = userInput.readLine();
                    if (input == null || input.equalsIgnoreCase("exit")) {
                        inChat = false;
                        serverHandlers.sendMessage(SerializeDeserializeMessage.serialize(new Instruction("exit")));
                        System.out.println("exiting chat...");
                        break;
                    }
                    if(!input.isEmpty()) {
                        serverHandlers.sendMessage(SerializeDeserializeMessage.serialize(new Instruction(input)));
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection lost");
                inChat = false;
            }
        });

        sender.start();

        try {
            sender.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private List<Room> getRooms(String serializedRooms) throws IOException {
        try{
            return SerializeDeserializeMessage.objectMapper.readValue(
                    serializedRooms,
                    new com.fasterxml.jackson.core.type.TypeReference<List<Room>>(){}
            );
        }catch (IOException e){
            return null;
        }
    }
    private void handleRoomState() throws IOException, InterruptedException {
        while (true) {
            System.out.println("1. Create room");
            System.out.println("2. Choose room");
            System.out.println("3. exit");

            String message = this.readLine(">");

            if (message == null || message.equals("3")) {
                serverHandlers.sendMessage(SerializeDeserializeMessage.serialize(new Instruction(message)));
                break;
            }

            serverHandlers.sendMessage(SerializeDeserializeMessage.serialize(new Instruction(message)));
            Instruction serverMessage = SerializeDeserializeMessage.deserialize(messageQueue.take(), Instruction.class);

            assert serverMessage != null;
            if (serverMessage.getMessage().equals("create-room")) {
                String roomName = this.readLine("room name: ");
                serverHandlers.sendMessage(SerializeDeserializeMessage.serialize(new Instruction(roomName)));
                String response = Objects.requireNonNull(SerializeDeserializeMessage.deserialize(messageQueue.take(), Instruction.class)).getMessage();
                System.out.println("this is the response: " + response);
                if (response.startsWith("room-already-exist")) {
                    System.out.println("room already exist please try again with different name");
                    break;
                } else if (response.equals("room-created")) {
                    System.out.println("room created successfully with name " + roomName);
                    break;
                }
            } else if (serverMessage.getMessage().equals("join-room")) {
                List<Room> rooms = this.getRooms(messageQueue.take());
                if(rooms != null) {
                    System.out.println("choose a room to join");
                    for (int j = 0; j < rooms.size(); j++) {
                        System.out.println((j + 1) + ": " + rooms.get(j).getName());
                    }
                    System.out.println("type exit to leave");
                    String roomChoice = this.readLine(">");
                    serverHandlers.sendMessage(SerializeDeserializeMessage.serialize(new Instruction(roomChoice)));
                    String response = Objects.requireNonNull(SerializeDeserializeMessage.deserialize(messageQueue.take(), Instruction.class)).getMessage();
                    System.out.println(response);
                    break;
                }
            } else {
                System.out.println(serverMessage.getMessage());
                break;
            }
        }
    }

    public void startClient() throws ClientExeption {
        try {
            this.serverHandlers.connect("localhost", port);
            System.out.println("Connected to server.");
            while (true) {
                String msg = messageQueue.take();
                Instruction instruction = SerializeDeserializeMessage.deserialize(msg, Instruction.class);
                switch (Objects.requireNonNull(instruction).getMessage()) {
                    case "InitialState":
                        handleInitialState();
                        break;
                    case "AuthState":
                        handleAuthState();
                        break;
                    case "roomState":
                        handleRoomState();
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
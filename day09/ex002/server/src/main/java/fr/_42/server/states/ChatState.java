package fr._42.server.states;

import fr._42.jsonMessages.Instruction;
import fr._42.jsonMessages.MessageSerialization;
import fr._42.jsonMessages.SerializeDeserializeMessage;
import fr._42.models.Message;
import fr._42.server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ChatState implements ConnectionState {
    private static final int MESSAGE_HISTORY_LIMIT = 30;

    @Override
    public void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) throws IOException {
        try {
            // Send initial chat state message
            writer.println(SerializeDeserializeMessage.serialize(new Instruction("chatState")));
            
            // Send welcome message
            String welcomeMsg = "Welcome to chat " + context.getAuthenticatedUser().getUsername() + " type exit to leave";
            writer.println(SerializeDeserializeMessage.serialize(new Instruction(welcomeMsg)));
            
            // Send message history
            List<Message> messageHistory = context.getMessageRepository().getLastMessages(
                context.getCurrentRoom().getId(), 
                MESSAGE_HISTORY_LIMIT
            );
            // Reverse to show oldest first
            Collections.reverse(messageHistory);
            
            // Send history marker
            writer.println(SerializeDeserializeMessage.serialize(new Instruction("--- Last " + messageHistory.size() + " messages ---")));
            
            // Send message history
            for (Message msg : messageHistory) {
                MessageSerialization historicMessage = new MessageSerialization(
                    msg.getId(),
                    msg.getSenderUsername(),
                    msg.getMessage_text(),
                    "CHAT_MESSAGE"
                );
                writer.println(SerializeDeserializeMessage.serialize(historicMessage));
            }
            
            // Send history end marker
            if (!messageHistory.isEmpty()) {
                writer.println(SerializeDeserializeMessage.serialize(new Instruction("------------------------")));
            }
            
            // Broadcast join message
            broadcastMessage(context, context.getAuthenticatedUser().getUsername() + " joined the chat", true);

            while (true) {
                String rawMessage = reader.readLine();
                if (rawMessage == null) {
                    context.getClientSocket().close();
                    ClientHandler.getAuthenticatedUsers().remove(context);
                    System.out.println("connexion exited " + context.getClientSocket().getInetAddress());
                    return;
                }

                Instruction instruction = SerializeDeserializeMessage.deserialize(rawMessage, Instruction.class);
                if (instruction == null || instruction.getMessage() == null) {
                    ClientHandler.getAuthenticatedUsers().remove(context);
                    System.out.println("connexion exited " + context.getClientSocket().getInetAddress());
                    break;
                }

                String messageText = instruction.getMessage();
                if (messageText.equalsIgnoreCase("exit")) {
                    handleExit(context, writer);
                    return;
                }

                // Create and save message
                final String finalMessageText = messageText;
                Message savedMessage = context.getMessageRepository().createMessage(() -> {
                    Message message = new Message();
                    message.setMessage_text(finalMessageText);
                    message.setSender(context.getAuthenticatedUser().getId());
                    message.setRoom_id(context.getCurrentRoom().getId());
                    return message;
                });

                // Create MessageSerialization object
                MessageSerialization chatMessage = new MessageSerialization(
                    savedMessage.getId(),
                    context.getAuthenticatedUser().getUsername(),
                    messageText,
                    "CHAT_MESSAGE"
                );

                // Broadcast message to room using MessageSerialization
                broadcastMessage(context, chatMessage, false);
            }
        } catch (IOException e) {
            handleDisconnection(context);
            throw e;
        }
    }

    private void handleExit(ClientHandler context, PrintWriter writer) throws IOException {
//        writer.println(SerializeDeserializeMessage.serialize(new Instruction("Exiting chat...")));
        if (context.getCurrentRoom() != null) {
            ClientHandler.getRoomUsers().get(context.getCurrentRoom().getId()).remove(context);
            broadcastMessage(context, context.getAuthenticatedUser().getUsername() + " left the chat", true);
        }
        context.setState(new RoomState());
    }

    private void handleDisconnection(ClientHandler context) throws IOException {
        if (context.getCurrentRoom() != null) {
            ClientHandler.getRoomUsers().get(context.getCurrentRoom().getId()).remove(context);
            broadcastMessage(context, context.getAuthenticatedUser().getUsername() + " disconnected from chat", true);
        }
        context.setState(new ConnectionTerminator());
    }

    private void broadcastMessage(ClientHandler sender, Object message, boolean useInstruction) {
        if (sender.getCurrentRoom() == null) return;
        
        ClientHandler.getRoomUsers().get(sender.getCurrentRoom().getId()).forEach(client -> {
            if (client == sender) {return;}
            try {
                PrintWriter clientWriter = new PrintWriter(client.getClientSocket().getOutputStream(), true);
                if (useInstruction && message instanceof String) {
                    // For system messages (join, leave, etc.)
                    clientWriter.println(SerializeDeserializeMessage.serialize(new Instruction((String) message)));
                } else {
                    // For chat messages using MessageSerialization
                    clientWriter.println(SerializeDeserializeMessage.serialize(message));
                }
            } catch (IOException e) {
                System.err.println("Error broadcasting message: " + e.getMessage());
            }
        });
    }
}

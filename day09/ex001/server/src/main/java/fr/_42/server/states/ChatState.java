package fr._42.server.states;

import fr._42.models.Message;
import fr._42.server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatState implements  ConnectionState{
    @Override
    public void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) throws IOException {
        writer.println("chatState");
        context.broadcastMessage(context.getAuthenticatedUser().getUsername() + " joined the chat");
        writer.println("welcome to chat " + context.getAuthenticatedUser().getUsername() + " type exit to leave");
        String line;

        while((line = reader.readLine()) != null) {
            if(line.equalsIgnoreCase("exit")) {
                writer.println("Exiting chat...");
                ClientHandler.getAuthenticatedUsers().remove(context);
                context.broadcastMessage(context.getAuthenticatedUser().getUsername() + " left the chat");
                context.setState(new InitialState());
                return;
            }
            final String messageText = line;
            context.getMessageRepository().createMessage(() -> {
                Message message = new Message();
                message.setMessage_text(messageText);
                message.setSender(context.getAuthenticatedUser().getId());
                return message;
            });
            String message = context.getAuthenticatedUser().getUsername() + " : " + line;
            context.broadcastMessage(message);
        }
        context.broadcastMessage(context.getAuthenticatedUser().getUsername() + " left the chat");
        context.setState(new ConnectionTerminator());
    }
}

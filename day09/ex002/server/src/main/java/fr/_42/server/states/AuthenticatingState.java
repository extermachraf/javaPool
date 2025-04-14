package fr._42.server.states;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringJoiner;

import fr._42.jsonMessages.Instruction;
import fr._42.jsonMessages.SerializeDeserializeMessage;
import fr._42.models.User;
import fr._42.server.ClientHandler;

public class AuthenticatingState implements ConnectionState {
    @Override
    public void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) throws IOException {
        writer.println(SerializeDeserializeMessage.serialize(new Instruction("AuthState", 0L)));
        String choice = reader.readLine();
        if(choice == null){
            System.out.println("connexion exited " + context.getClientSocket().getInetAddress());
            return;
        }
        if(!(choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
            writer.println("can you  please choose one of the following choices!!");
            context.setState(new InitialState());
            return;
        }
        if (choice.equals("3")) {
            writer.println("exiting...");
            context.setState(new ConnectionTerminator());
            return;
        }

        String username = reader.readLine();
        String password = reader.readLine();
        System.out.printf("{%s}  {%s}", username, password);

        User user = null;
        if (choice.equals("1")) {
            user = context.getUsersService().SignUp(username, password);
        } else if (choice.equals("2")) {
            user = context.getUsersService().SignIn(username, password);
        }else {
            writer.println("enter a valid number please");
            context.setState(new InitialState());
        }

        System.out.println(user);
        if (user != null) {
            for(ClientHandler client : ClientHandler.getAuthenticatedUsers()){
                System.out.println("Checking against user: " + client.getAuthenticatedUser().getId() + " vs " + user.getId());
                if(client.getAuthenticatedUser().equals(user)) {
                    writer.println("You are already authenticated");
                    context.setState(new InitialState());
                    return;
                }
            }
            context.setAuthenticatedUser(user);
            ClientHandler.getAuthenticatedUsers().add(context);
            writer.println("Welcome " + user.getUsername());
            context.setState(new RoomState());
        } else {
            writer.println("Invalid credentials, try again.");
            context.setState(new InitialState());
        }
    }
}
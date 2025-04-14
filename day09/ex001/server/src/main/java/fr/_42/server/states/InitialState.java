package fr._42.server.states;

import fr._42.server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class InitialState implements ConnectionState {
    @Override
    public void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) {
        writer.println("InitialState");
        writer.println("Please choose an option:\\n  1. register\\n  2. Login\\n  3. Exit");
        context.setState(new AuthenticatingState());
    }
}

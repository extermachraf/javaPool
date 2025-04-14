package fr._42.server.states;

import fr._42.server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConnectionTerminator implements ConnectionState{

    @Override
    public void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) throws IOException{
        writer.println("Terminated");
    }
}

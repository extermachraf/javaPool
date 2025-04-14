package fr._42.server.states;

import fr._42.server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface ConnectionState {
    void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) throws IOException;
}

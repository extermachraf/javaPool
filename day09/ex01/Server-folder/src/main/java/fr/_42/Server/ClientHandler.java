package fr._42.Server;

import fr._42.models.User;

import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler {
    private final PrintWriter writer;
    private final Socket socket;

    public ClientHandler(User user, PrintWriter writer, Socket socket) {
        this.writer = writer;
        this.socket = socket;
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public boolean isConnected() {
        return !socket.isClosed();
    }
}
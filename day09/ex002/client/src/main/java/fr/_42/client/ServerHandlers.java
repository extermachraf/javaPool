package fr._42.client;

import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandlers {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected;
    @Setter
    private MessageListener listener;

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    public void connect(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        connected = true;

        new Thread(this::receiveMessages).start();
    }

    public void disconnect() {
        System.out.println("disconnecting");
        System.exit(0);
    }

    public void sendMessage(String message) {
        if (connected && out != null) {
            out.println(message);
        }
    }

    private void receiveMessages() {
        try {
            String serverResponse;
            while (connected && (serverResponse = in.readLine()) != null) {
                if(listener != null) {
                    listener.onMessageReceived(serverResponse);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        } finally {
            connected = false;
            this.disconnect();
        }
    }
}

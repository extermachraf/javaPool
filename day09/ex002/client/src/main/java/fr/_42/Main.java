package fr._42;

import fr._42.client.Client;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1)
            throw new RuntimeException("you need to enter only one argument in the format of --port=PORT");
        if(!args[0].startsWith("--port="))
            throw new RuntimeException("the port should be specified as --port=PORT");
        if(!args[0].substring("--port=".length()).matches("[0-9]+"))
            throw new RuntimeException("the port should be specified as --port=PORT : PORT should be a number");
        Client client = new Client(Integer.parseInt(args[0].substring("--port=".length())));
        client.startClient();
    }
}
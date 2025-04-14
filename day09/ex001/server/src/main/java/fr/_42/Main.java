package fr._42;

import fr._42.config.SocketsApplicationConfig;
import fr._42.exeptions.ServException;
import fr._42.repositories.messages.MessageRepository;
import fr._42.repositories.messages.MessageRepositoryImpl;
import fr._42.server.Server;
import fr._42.services.UsersServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1)
            throw new RuntimeException("you need to enter only one argument in the format of --port=PORT");
        if(!args[0].startsWith("--port="))
            throw new RuntimeException("the port should be specified as --port=PORT");
        if(!args[0].substring("--port=".length()).matches("[0-9]+"))
            throw new RuntimeException("the port should be specified as --port=PORT : PORT should be a number");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        Server server = new Server(Integer.parseInt(args[0].substring("--port=".length())), context.getBean(UsersServiceImpl.class), context.getBean(MessageRepositoryImpl.class));
        try {
            server.StartServer();
        } catch (ServException e){
            System.out.println(e.getMessage());
        }
    }
}
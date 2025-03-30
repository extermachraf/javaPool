package fr._42;

import fr._42.config.SocketsApplicationConfig;
import fr._42.exeptions.SerevrExeption;
import fr._42.models.User;
import fr._42.repositories.UserRepositoryImpl;
import fr._42.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.naming.Context;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1)
            throw new RuntimeException("you need to enter only one argument in the format of --port=PORT");
        if(!args[0].startsWith("--port="))
            throw new RuntimeException("the port should be specified as --port=PORT");
        if(!args[0].substring("--port=".length()).matches("[0-9]+"))
            throw new RuntimeException("the port should be specified as --port=PORT : PORT should be a number");


//        Server server = new Server(Integer.parseInt(args[0].substring("--port=".length())));
//        try {
//            server.StartServer();
//        } catch (SerevrExeption e){
//            System.out.println(e.getMessage());
//        }
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
//        UserRepositoryImpl userImpl =  context.getBean(UserRepositoryImpl.class);
//        User user =  userImpl.createUser(new User(null, "achraf", "123"));
//        System.out.println(user);
    }
}
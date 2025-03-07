package fr._42;
import fr._42.Server.Server;
import fr._42.config.SocketsApplicationConfig;
import fr._42.models.Message;
import fr._42.models.User;
import fr._42.repositpries.message.MessageRepoExeption;
import fr._42.repositpries.message.MessageRepository;
import fr._42.repositpries.message.MessageRepositoryImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws MessageRepoExeption {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
//        int port = 12345;
//        Server server = new Server(port);
//        server.start();
        Server server = context.getBean(Server.class);
        server.start();
    }
}
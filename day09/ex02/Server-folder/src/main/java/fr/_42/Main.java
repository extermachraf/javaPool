package fr._42;
import fr._42.Server.Server;
import fr._42.config.SocketsApplicationConfig;
import fr._42.models.Chatroom;
import fr._42.models.Message;
import fr._42.models.User;
import fr._42.repositpries.chatroom.ChatroomRepository;
import fr._42.repositpries.chatroom.ChatroomsRepositoryImpl;
import fr._42.repositpries.message.MessageRepoExeption;
import fr._42.repositpries.message.MessageRepository;
import fr._42.repositpries.message.MessageRepositoryImpl;
import fr._42.repositpries.user.UserRepoExeption;
import fr._42.services.UsersServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class Main {
    public static void main(String[] args) throws MessageRepoExeption, UserRepoExeption {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        Server server = context.getBean(Server.class);
        server.start();
//        server.start();
//        Server server = context.getBean(Server.class);
//        server.start();
//        context.getBean(UsersServiceImpl.class).signUp("achraf", "123");
//        ChatroomRepository room =  context.getBean(ChatroomsRepositoryImpl.class);
//        room.delete(2L);
    }
}
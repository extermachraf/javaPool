package repo.app;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.sql.DataSource;

import repo.repositories.Chatroom;
import repo.repositories.DatabaseConfig;
import repo.repositories.Message;
import repo.repositories.MessagesRepository;
import repo.repositories.MessagesRepositoryJdbcImpl;
import repo.repositories.User;
import repo.repositories.exeptions.NotSavedSubEntityException;

/**
 * Hello world!
 *
 */
public class Program {
    public static void main(String[] args) throws IOException {
        try{
            // connecting to data base
            DatabaseConfig databaseconfig = new DatabaseConfig();
            DataSource datasource = databaseconfig.gDataSource();    
            MessagesRepository repository = new MessagesRepositoryJdbcImpl(datasource);
            ///////////
//
            User creator = new User(2L, "user", "user", new ArrayList<>(), new ArrayList<>());
            User author = creator;
            Chatroom room = new Chatroom(2L, "room", creator, new ArrayList<>());
            Message message = new Message( author, room, "Hello!", LocalDateTime.now());
            repository.save(message);
//            Optional<Message> message =  repository.findById(32L);
//            if(message.isPresent()){
//                System.out.println(message.get());
//            }else {
//                System.out.println("Message not found");
//            }
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e){
            System.err.println(e.getMessage());
        }
    }
}

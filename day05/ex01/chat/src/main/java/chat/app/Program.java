package chat.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import javax.sql.DataSource;

import chat.repositories.DatabaseConfig;
import chat.repositories.Message;
import chat.repositories.MessagesRepository;
import chat.repositories.MessagesRepositoryJdbcImpl;
/**
 * Hello world!
 *
 */
public class Program {
    public static void main(String[] args) {

        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
            System.out.print("-->");
            String input = reader.readLine();
            if (!input.matches("\\d+"))
                throw new  IllegalArgumentException("please enter a valid input");
    
            Long Id = Long.parseLong(input);
            DatabaseConfig databaseconfig = new DatabaseConfig();
    
            DataSource datasource = databaseconfig.gDataSource();
    
            MessagesRepository repository = new MessagesRepositoryJdbcImpl(datasource);
    
            Optional<Message> message = repository.findById(Id);
    
            // Display the result
            message.ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Message not found"));

        } catch(IOException e){
            System.err.println("ERROR");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}

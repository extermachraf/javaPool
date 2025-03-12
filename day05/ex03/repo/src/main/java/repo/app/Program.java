package repo.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import javax.sql.DataSource;

import repo.repositories.DatabaseConfig;
import repo.repositories.Message;
import repo.repositories.MessagesRepository;
import repo.repositories.MessagesRepositoryJdbcImpl;
import repo.repositories.exeptions.NotSavedSubEntityException;

/**
 * Hello world!
 *
 */
public class Program {
    public static void main(String[] args) throws IOException {
        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
            System.out.print("-->");
            String input = reader.readLine();
            if (!input.matches("\\d+"))
                throw new  IllegalArgumentException("please enter a valid input");
    
               
                
            // connecting to database
            DatabaseConfig databaseconfig = new DatabaseConfig();
            DataSource datasource = databaseconfig.gDataSource();    
            MessagesRepository repository = new MessagesRepositoryJdbcImpl(datasource);
            ///////////
            

            // get the message by id and display it
            Optional<Message> message = repository.findById(Long.parseLong(input));
            // Display the result
            message.ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Message not found"));
            if (message.isPresent()){
                Message m = message.get();
                m.setText("wa7ed salmou 3alaykoum");
                m.setCreatedAt(null);

                repository.update(m);
            }
            ////////////   
        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e){
            System.err.println(e.getMessage());
        }
    }
}

package repo.app;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import repo.repositories.DatabaseConfig;
import repo.repositories.User;
import repo.repositories.exeptions.NotSavedSubEntityException;
import repo.repositories.findall.UsersRepository;
import repo.repositories.findall.UsersRepositoryJdbcImpl;

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

            //////////
            UsersRepository usersRepository = new UsersRepositoryJdbcImpl(datasource);
            // Example 1: Fetch the first 10 users (Page 0, Size 10)
            List<User> usersPage1 = usersRepository.findAll(0, 2);
            System.out.println("Page 1:");
            for (User user : usersPage1) {
                System.out.println(user);
            }

            // Example 2: Fetch the next 10 users (Page 1, Size 10)
            // List<User> usersPage2 = usersRepository.findAll(1, 10);
            // System.out.println("Page 2:");
            // for (User user : usersPage2) {
            //     System.out.println(user.toString());
            // }

            // // Example 3: Fetch 20 users on page 2 (Page 2, Size 20)
            // List<User> usersPage3 = usersRepository.findAll(2, 20);
            // System.out.println("Page 3:");
            // for (User user : usersPage3) {
            //     System.out.println(user.toString());
            // }


        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e){
            System.err.println(e.getMessage());
        }
    }
}

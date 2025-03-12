package repo.app;

import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import repo.repositories.DatabaseConfig;
import repo.repositories.User;
import repo.repositories.exeptions.NotSavedSubEntityException;
import repo.repositories.findall.UsersRepository;
import repo.repositories.findall.UsersRepositoryJdbcImpl;

public class Program {
    public static void main(String[] args) throws IOException {
        try{    
            // connecting to data base
            DatabaseConfig databaseconfig = new DatabaseConfig();
            DataSource datasource = databaseconfig.gDataSource();

            //////////
            UsersRepository usersRepository = new UsersRepositoryJdbcImpl(datasource);
            // Example 1: Fetch the first 10 users (Page 0, Size 10)
            List<User> usersPage1 = usersRepository.findAll(0, 3);
            System.out.println("Page 1:");
            for (User user : usersPage1) {
                System.out.println(user);
            }


        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e){
            System.err.println(e.getMessage());
        }
    }
}

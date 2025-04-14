package fr._42;

import fr._42.interfaces.UsersRepository;
import fr._42.models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.BadAttributeValueExpException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args){
        try{
            ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
            UsersRepository usersRepository = (UsersRepository) context.getBean("userJdbcImpl");
            usersRepository.delete(3L);
        }
        catch (SQLException | BadAttributeValueExpException  e){
            System.out.println(e.getMessage());
        }
    }
}
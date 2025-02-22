package fr._42;

import fr._42.interfaces.UsersRepository;
import fr._42.models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.BadAttributeValueExpException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        try{
            ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
            UsersRepository usersRepository = (UsersRepository) context.getBean("userJdbcTemplate");

//            usersRepository.update(new User(1L, "abdo", "abdoelghafor+1@gmail.com"));
            usersRepository.delete(5L);
        }
        catch (SQLException | BadAttributeValueExpException  e){
            System.out.println(e.getMessage());
        }
    }
}
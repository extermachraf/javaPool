package fr._42;

import fr._42.config.ApplicationConfig;
import fr._42.models.User;
import fr._42.services.UsersServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        DataSource dataSource = context.getBean("dataSource", DataSource.class);
//        System.out.println("this is the dataSource" +  dataSource);
//        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
//        System.out.println("this is the jdbcTemplate" + jdbcTemplate);
        UsersServiceImpl usersService = (UsersServiceImpl) context.getBean("usersServiceImpl");
//        usersService.save(new User("elon@gmail.com", "sirtn3ess#kl"));
//        String email = "user@postgresql.org";
//        usersService.signUp(email);
        Optional<User> u = usersService.findByEmail("user@postgresql.org");
        u.ifPresent(user -> System.out.println(user.toString()));
    }
}
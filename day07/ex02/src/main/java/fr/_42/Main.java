package fr._42;
import fr._42.services.OrmManager;
import fr._42.tables.Messages;
import fr._42.tables.User;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            User user = new User();
            User user1 = new User("achraf", "el kouch", 25);
            User updatedUser = new User(1L,null, "el kouch", 25);
            User kabo = new User("kabo", "nabo", 5);
            Messages messages = new Messages();
            OrmManager orm = new OrmManager(user.getClass(), messages.getClass());
            orm.createTables();
            System.out.println("--------------------- save user -------------------------");
            orm.save(user1);
            orm.save(kabo);
            System.out.println("---------------------- update user ------------------------");
            orm.update(updatedUser);
            System.out.println("---------------------- find user ------------------------");
            User user3 = orm.findById(2l, updatedUser.getClass());
            System.out.println(user3.toString());
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | NoSuchFieldException |
                 InstantiationException e) {
            System.err.println(e.getMessage());
        }
    }
}
package spring_service.services;

import fr._42.config.ApplicationConfig;
import fr._42.models.User;
import fr._42.services.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring_service.config.TestApplicationConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(classes = TestApplicationConfig.class)
public class UsersServiceImplTest {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Test
//    public void testSchemaCreation() {
//        // Verify that the table was created
//        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
//        assertEquals(0, count); // Initially, the table should be empty
//    }
    @Autowired
    private UsersService usersService;

    @Test
    public void testSignUpAndPasswordGeneration() {
        String testEmail = "test@example.com";
        String tempPassword = usersService.signUp(testEmail);

        assertNotNull(tempPassword, "Password should not be null");
        assertEquals(8, tempPassword.length(), "Password should be 8 characters");

        Optional<User> savedUser = usersService.findByEmail(testEmail);
        assertTrue(savedUser.isPresent(), "User should be present");
        assertEquals(testEmail, savedUser.get().getEmail(), "Emails should match");
        assertEquals(tempPassword, savedUser.get().getPassword(), "Passwords should match");
    }

    @Test
    public void testDuplicateEmailSignUp() {
        String email = "duplicate@test.com";
        usersService.signUp(email);

        Exception exception = assertThrows(Exception.class, () -> {
            usersService.signUp(email);
        }, "Should throw on duplicate email");

        assertNotNull(exception, "Exception should be thrown");
    }

    @Test
    public void testFindNonExistentUser() {
        Optional<User> user = usersService.findByEmail("nonexistent@test.com");
        assertFalse(user.isPresent(), "User should not be found");
    }

    @Test
    public void testUserLifecycle() {
        // Create
        String email = "lifecycle@test.com";
        String password = usersService.signUp(email);

        // Read
        User createdUser = usersService.findByEmail(email).orElseThrow();
        assertNotNull(createdUser.getId(), "ID should be generated");

        // Update
        String newPassword = "newPassword123";
        createdUser.setPassword(newPassword);
        usersService.update(createdUser);

        User updatedUser = usersService.findById(createdUser.getId());
        assertEquals(newPassword, updatedUser.getPassword(), "Password should update");

        // Delete
        usersService.delete(updatedUser.getId());
        assertFalse(usersService.findByEmail(email).isPresent(), "User should be deleted");
    }
}
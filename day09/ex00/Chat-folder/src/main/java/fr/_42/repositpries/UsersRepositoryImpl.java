package fr._42.repositpries;

import fr._42.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository{

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public UsersRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isDatabaseConnected() {
        try {
            jdbcTemplate.execute("SELECT 1"); // Simple query
            return true;
        } catch (Exception e) {
            return false;
        }
    }


   @Override
    public Optional<User> findById(long id) throws UserRepoExeption {
       try{
           User user = this.jdbcTemplate.queryForObject("SELECT id, name, password FROM users WHERE ID = ?", (rs, rowNum) -> {
               User u = new User();
               u.setId(rs.getLong("id"));
               u.setFullName(rs.getString("name"));
               u.setPassword(rs.getString("password"));
               return u;
           }, id);
           return Optional.ofNullable(user);
       } catch(Exception e){
           throw new UserRepoExeption("error finding user by id: " + id + " " + e.getMessage());
       }
   }

   @Override
    public List<User> findAll() throws UserRepoExeption {
        try{
            return this.jdbcTemplate.query("SELECT id, name, password FROM users", (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setFullName(rs.getString("name"));
                u.setPassword(rs.getString("password"));
                return u;
            });
        } catch (Exception e){
            throw new UserRepoExeption("error finding all users" + e.getMessage());
        }
   }

   @Override
    public void save(User entity) throws Exception{
        try{
            jdbcTemplate.update("INSERT INTO users (name, password) VALUES (?, ?)", entity.getFullName(), entity.getPassword());
        } catch (Exception e){
            throw new UserRepoExeption("Error saving user: " + e.getMessage());
        }
   }

   @Override
    public void update(User entity) throws UserRepoExeption {
        try {
            jdbcTemplate.update("UPDATE INTO users (name, password) VALUES (?, ?) WHERE id = ?", entity.getFullName(), entity.getPassword(), entity.getId());
        } catch (Exception e){
            throw new UserRepoExeption("Error saving user: " + e.getMessage());
        }
   }

   @Override
    public void delete(long id) throws UserRepoExeption {
        try{
            int rowsAffected =  jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
            if(rowsAffected > 0){
                System.err.println("User with id " + id + " was deleted.");
            }else{
                System.err.println("User with id " + id + " was not deleted.");
            }
        } catch (Exception e){
            throw new UserRepoExeption("error deleting user: " + e.getMessage());
        }
   }

   @Override
    public Optional<User> findByUsername(String username) {
        try{
            User user = jdbcTemplate.queryForObject("SELECT id, name, password FROM users WHERE name = ?", (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setFullName(rs.getString("name"));
                u.setPassword(rs.getString("password"));
                return u;
            }, username);
            return Optional.ofNullable(user);
        }catch(EmptyResultDataAccessException e){
            System.err.println("No User found with name " + e.getMessage());
            return Optional.empty();
        } catch(Exception e){
            System.err.println("Error finding user with name " + e.getMessage());
            return Optional.empty();
        }

   }
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}

package fr._42.repositories;

import fr._42.interfaces.UsersRepository;
import fr._42.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.management.BadAttributeValueExpException;
import java.util.*;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private static final Logger logger = LoggerFactory.getLogger(UsersRepositoryJdbcTemplateImpl.class);
    NamedParameterJdbcTemplate ds;
    public UsersRepositoryJdbcTemplateImpl(NamedParameterJdbcTemplate ds) {
        this.ds = ds;
    }

    @Override
    public Optional<User> findByEmail(String email){
        if (email == null || email.isEmpty()){
            logger.warn("Email is null or empty");
            return Optional.empty();
        }
        String sql = "select * from \"user\" where email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        try {

        User user = ds.queryForObject(sql, params, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setEmail(rs.getString("email"));
            u.setFullName(rs.getString("full_name"));
            return u;
        });
        return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            logger.error("Error finding user by email: {}", email, e);
            return Optional.empty();
        }
    }

    @Override
    public User findById(Long id){
        if (id == null){
            logger.warn("id is null");
            return null;
        }
        String sql = "select * from \"user\" where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            User user = ds.queryForObject(sql, params, (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setFullName(rs.getString("full_name"));
                return u;
            });
            return user;
        } catch (DataAccessException e) {
            logger.error("Error no user exist with id: {}", id, e);
            return null;
        }
    }

    @Override
    public List<User> findAll(){
        String sql = "select * from \"user\"";

        try {
            return  ds.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setFullName(rs.getString("full_name"));
                return user;
            });
        } catch (DataAccessException e) {
            logger.error("Error finding users", e);
            return null;
        }
    }
    @Override
    public void save(User entity) throws DataAccessException, BadAttributeValueExpException {
        if (entity.getFullName() == null || entity.getFullName().isEmpty()) {
            logger.warn("Full name is null or empty");
            throw new NullPointerException("full name is null or empty");
        }
        else if (entity.getEmail() == null || entity.getEmail().isEmpty()) {
            logger.warn("Email is null or empty");
            throw new NullPointerException ("email is null or empty");
        }

//        check if the user exist befor update
        if(Optional.ofNullable(this.findByEmail(entity.getEmail())).isPresent()){
            logger.error("user already exist with email: {}", entity.getEmail());
            throw new BadAttributeValueExpException("user already exist with email");
        }
        String sql = "INSERT INTO \"user\" (\"full_name\", \"email\") VALUES (:fullName, :email)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("fullName", entity.getFullName())
                .addValue("email", entity.getEmail());
        try {
            ds.update(sql, params);
            logger.info("User saved successfully: {}", entity);
        }
        catch (DataAccessException e) {
            logger.error("Error saving user: {}", entity, e);
            throw e;
        }

    }

    @Override
    public void update(User entity) throws BadAttributeValueExpException{
        if (entity == null) {
            logger.error("User entity is null");
            throw new NullPointerException("User entity is null");
        }
        if (entity.getId() == null) {
            logger.error("User ID is null");
            throw new NullPointerException("User ID is null");
        }
        if (entity.getFullName() == null || entity.getFullName().isEmpty()) {
            logger.error("Full name is null or empty");
            throw new NullPointerException("Full name is null or empty");
        }
        if (entity.getEmail() == null || entity.getEmail().isEmpty()) {
            logger.error("Email is null or empty");
            throw new NullPointerException("Email is null or empty");
        }

        if(this.findById(entity.getId()) == null){
            logger.error("user does not exist with id: {}", entity.getId());
            throw new BadAttributeValueExpException("user does not exist with id: " + entity.getId());
        }

        String sql = "UPDATE \"user\" SET full_name = :fullName, email = :email WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("fullName", entity.getFullName())
                .addValue("email", entity.getEmail());
        try {
            int rowsUpdated = ds.update(sql, params);
            if (rowsUpdated > 0) {
                logger.info("User updated successfully: {}", entity);
            } else {
                logger.warn("No user found with ID: {}", entity.getId());
            }
        } catch (DataAccessException e) {
            logger.error("Error updating user: {}", entity, e);
        }
    }

    @Override
    public void delete(Long id){
        if (id == null) {
            logger.error("User ID is null");
            throw new NullPointerException("User ID is null");
        }

        String sql = "DELETE FROM \"user\" WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            int rowsDeleted = ds.update(sql, params);
            if (rowsDeleted > 0) {
                logger.info("User deleted successfully with ID: {}", id);
            } else {
                logger.warn("No user found with ID: {}", id);
            }
        } catch (DataAccessException e) {
            logger.error("Error deleting user with ID: {}", id, e);
        }
    }
}

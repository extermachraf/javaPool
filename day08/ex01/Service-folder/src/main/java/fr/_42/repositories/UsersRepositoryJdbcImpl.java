package fr._42.repositories;

import fr._42.interfaces.UsersRepository;
import fr._42.models.User;

import javax.management.BadAttributeValueExpException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    DataSource ds;
    UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.ds = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException, BadAttributeValueExpException{
        if(email == null || email.isEmpty())
            throw new BadAttributeValueExpException("null eamil is not allowed");
        String query = String.format("select * from users where email = '%s'", email);
        try(Connection con = ds.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            if(rs.next()){
                return Optional.of(new User(rs.getLong("id"),
                    rs.getString("full_name"), rs.getString("email")));
            } else
                return Optional.empty();
        }
    }

    @Override
    public User findById(Long id) throws SQLException{
        String query = String.format("select * from users where id = '%d'", id);
        try(Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            if(rs.next()){
                return new User(rs.getLong("id"), rs.getString("full_name"), rs.getString("email") );
            }
            else
                return null;
        }
    }

    @Override
    public List<User> findAll() throws SQLException{
        List<User> users = new ArrayList<>();
        String query = "select * from users";
        try(Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)){
            while(rs.next()){
                users.add(new User(rs.getLong("id"), rs.getString("full_name"), rs.getString("email")));
            }
        }
        if(users.isEmpty())
            return null;
        return users;
    }

    @Override
    public void save(User entity) throws SQLException, BadAttributeValueExpException {
        if (entity == null) {
            throw new BadAttributeValueExpException("You cannot insert a null user.");
        }

        String sql = String.format("INSERT INTO users (email, full_name) VALUES ('%s', '%s')",
                entity.getEmail(), entity.getFullName());

        try (Connection conn = this.ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Inserted " + entity.getEmail() + " " + entity.getFullName());
        }
    }

    @Override
    public void update(User entity) throws BadAttributeValueExpException, SQLException{
        if(entity == null)
            throw new BadAttributeValueExpException("You cannot update a null user.");
        else if (entity.getId() == null)
            throw new BadAttributeValueExpException("You cannot update a user with a null id.");
        if(this.findById(entity.getId()) == null)
            throw new BadAttributeValueExpException("You cannot update a user with a non existing id.");
        String query = String.format("UPDATE users SET email = '%s', full_name = '%s' WHERE id = '%d'",
                entity.getEmail(), entity.getFullName(), entity.getId());
        try (Connection conn = this.ds.getConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Updated " + entity.getEmail() + " " + entity.getFullName());
        }
    }

    @Override
    public void delete(Long id) throws SQLException , BadAttributeValueExpException{
        if(this.findById(id) == null)
            throw new BadAttributeValueExpException("You cannot delete a non existing user.");
        String query = String.format("DELETE FROM users WHERE id = '%d'", id);
        try (Connection conn = this.ds.getConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Deleted " + id);
        }
    }
}

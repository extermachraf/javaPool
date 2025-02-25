package fr._42.repositories;

import fr._42.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("jdbcTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password")
    );

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        System.out.println("qualified usersRepositoryJdbcTemplateImpl is created");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                new Object[]{id},
                userRowMapper
        );
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public void save(User user) {
        System.out.println("save from UsersRepositoryJdbcTemplateImpl is run");
        jdbcTemplate.update(
                "INSERT INTO users (email, password) VALUES (?, ?)",
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE users SET email = ?, password = ? WHERE id = ?",
                user.getEmail(),
                user.getPassword(),
                user.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE email = ?",
                new Object[]{email},
                userRowMapper
        );
        return users.stream().findFirst();
    }
}
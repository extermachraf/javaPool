package fr._42.repositories;

import fr._42.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UsersRepository {

    String test;
//    JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
    @Autowired
    public void setTest(String test) {
        this.test = test;
        System.out.println(test);
    }
    @Override
    public User createUser(User user){
        return null;
//        String sql = "INSERT INTO users (username, password) VALUES (?, ?) RETURNING id";
//        Long id = jdbcTemplate.queryForObject(sql, Long.class, user.getUsername(), user.getPassword());
//        return new User(id, user.getUsername(), user.getPassword());
    }
}

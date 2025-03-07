package fr._42.repositpries.chatroom;

import fr._42.models.Chatroom;
import fr._42.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChatroomsRepositoryImpl implements ChatroomRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Chatroom> chatroomRowMapper = (rs, rowNum) -> {
        User creator = new User(
                rs.getLong("user_id"),
                rs.getString("name")
        );

        return new Chatroom(
                rs.getLong("id"),
                rs.getString("room_name"),
                creator,
                rs.getTimestamp("created_at")
        );
    };

    @Autowired
    public ChatroomsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Chatroom create(String name, Long createdBy){
        try{
            jdbcTemplate.update("INSERT INTO chatrooms (name, created_by) values (?, ?)", name, createdBy);
            Optional<Chatroom> finder = this.findByName(name);
            return finder.orElse(null);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Failed to create chatroom: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<Chatroom> findById(Long id){
        String query = "select c.id, c.name AS room_name, c.created_at, u.id AS user_id, u.name FROM chatrooms c JOIN users u ON c.created_by = u.id WHERE c.id = ?";

        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, chatroomRowMapper, id));
        } catch (Exception e){
            System.out.println("Failed to find chatroom: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Chatroom> findAll(){
        String query = "SELECT c.id, c.name AS room_name, c.created_at, u.id AS user_id, u.name FROM chatrooms c JOIN users u ON c.created_by = u.id";
        try {
            return jdbcTemplate.query(query, chatroomRowMapper);
        }catch (Exception e){
            System.out.println("Failed to find chatrooms: " + e.getMessage());
            return null;
        }
    }


    @Override
    public void delete(Long chatroomId){
        String query = "DELETE FROM chatrooms WHERE id = ?";
        try{
            jdbcTemplate.update(query, chatroomId);
        } catch (Exception e){
            System.out.println("Failed to delete chatroom: " + e.getMessage());
        }
    }

    private Optional<Chatroom> findByName(String name){
        String sql = " select c.id, c.name, c.created_at, u.id AS user_id, u.name FROM chatrooms c JOIN users u ON c.created_by = u.id WHERE c.name= ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, chatroomRowMapper, name));
        } catch(Exception e) {
            return Optional.empty();
        }
    }
}

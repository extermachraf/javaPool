package fr._42.repositories.room;

import fr._42.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomRepositoryImpl implements RoomRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Room createRoom(String roomName){
        String sql = "insert into rooms (name) values (?) RETURNING id, name";
        try {
            return jdbcTemplate.query(
                    sql,
                    ps -> ps.setString(1, roomName),
                    (rs, rowNum) -> new Room(rs.getLong("id"), rs.getString("name"))
            ).stream().findFirst().orElse(null);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Room> getAllRooms() {
        String sql = "SELECT id, name FROM rooms";
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> new Room(rs.getLong("id"), rs.getString("name"))
            );
        } catch (DataAccessException e) {
            return null;
        }
    }
}

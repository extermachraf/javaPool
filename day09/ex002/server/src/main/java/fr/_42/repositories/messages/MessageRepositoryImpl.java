package fr._42.repositories.messages;

import fr._42.exeptions.ServException;
import fr._42.models.Message;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;
import java.util.List;

@Service
public class MessageRepositoryImpl implements MessageRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message createMessage(@NonNull Supplier<Message> messageSupplier) throws ServException {
        String query = "insert into messages (sender_id, message_text, room_id) values (?, ?, ?) Returning *";
        try {
            return jdbcTemplate.query(
                    query,
                    ps -> {
                        ps.setLong(1, messageSupplier.get().getSender());
                        ps.setString(2, messageSupplier.get().getMessage_text());
                        ps.setLong(3, messageSupplier.get().getRoom_id());
                    },
                    rs->{
                        if(rs.next()) {
                            Message m = new Message();
                            m.setId(rs.getLong(1));
                            m.setSender(rs.getLong(2));
                            m.setMessage_text(rs.getString(3));
                            m.setTimestamp(rs.getTimestamp(4));
                            m.setRoom_id(rs.getLong(5));
                            return m;
                        }
                        throw new EmptyResultDataAccessException("No message was created", 1);
                    }
            );
        } catch (DataIntegrityViolationException e){
            throw new ServException("Error creating message");
        } catch (EmptyResultDataAccessException e){
            throw new ServException("No message was created");
        }
    }

    public List<Message> getLastMessages(Long roomId, int limit) {
        String query = "SELECT m.*, u.username FROM messages m " +
                      "JOIN users u ON m.sender_id = u.id " +
                      "WHERE m.room_id = ? " +
                      "ORDER BY m.sending_time DESC " +
                      "LIMIT ?";
        
        return jdbcTemplate.query(query,
            (rs, rowNum) -> {
                Message message = new Message();
                message.setId(rs.getLong("id"));
                message.setSender(rs.getLong("sender_id"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTimestamp(rs.getTimestamp("sending_time"));
                message.setRoom_id(rs.getLong("room_id"));
                message.setSenderUsername(rs.getString("username"));
                return message;
            },
            roomId, limit
        );
    }
}

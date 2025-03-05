package fr._42.repositpries.message;

import fr._42.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MessageRepositoryImpl implements MessageRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Message message) throws MessageRepoExeption {
        try {
            jdbcTemplate.update(
                    "INSERT INTO messages (sender_id, message_text, sent_time) VALUES (?, ?, ?)",
                    message.getSender().getId(),
                    message.getText(),
                    Timestamp.valueOf(message.getTimestamp())
            );
        } catch (Exception e) {
            throw new MessageRepoExeption("error while saving message" + e.getMessage());
        }

    }

}

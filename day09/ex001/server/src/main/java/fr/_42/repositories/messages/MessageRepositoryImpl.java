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

@Service
public class MessageRepositoryImpl implements MessageRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message createMessage(@NonNull Supplier<Message> messageSupplier) throws ServException {
        String query = "insert into messages (sender_id, message_text) values (?, ?) Returning *";
        try {
            return jdbcTemplate.query(
                    query,
                    ps -> {
                        ps.setLong(1, messageSupplier.get().getSender());
                        ps.setString(2, messageSupplier.get().getMessage_text());
                    },
                    rs->{
                        if(rs.next()) {
                            Message m = new Message();
                            m.setId(rs.getLong(1));
                            m.setSender(rs.getLong(2));
                            m.setMessage_text(rs.getString(3));
                            m.setTimestamp(rs.getTimestamp(4));
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
}

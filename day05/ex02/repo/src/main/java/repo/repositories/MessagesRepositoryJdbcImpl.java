package repo.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.sql.DataSource;

import repo.repositories.exeptions.NotSavedSubEntityException;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) throws NotSavedSubEntityException {
        String query = "SELECT m.id , m.text, m.created_at, " +
                        "u.id AS author_id, u.login AS author_login, u.password AS author_password, " +
                        "r.id AS room_id , r.name AS room_name " +
                        "FROM messages m " +
                        "JOIN users u ON m.author_id = u.id " +
                        "JOIN chatrooms r ON m.room_id = r.id " +
                        "WHERE m.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Map the result to a Message object
                    User author = new User(rs.getLong("author_id"), rs.getString("author_login"),
                            rs.getString("author_password"), new ArrayList<>(), new ArrayList<>());

                    Chatroom room = new Chatroom(rs.getLong("room_id"), rs.getString("room_name"), author, new ArrayList<>());

                    Timestamp timestamp = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = null;
                    if (timestamp != null) {
                        createdAt = timestamp.toLocalDateTime();
                    }
                    Message message = new Message(
                            rs.getLong("id"),
                            author,
                            room,
                            rs.getString("text"),
                            createdAt
                    );
                    return Optional.of(message);
                } else {
                    throw new NotSavedSubEntityException("Message not found in the database.");
                }
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("SQL error occurred while retrieving the message.");
        }
    }

    @Override
    public  void save(Message message) throws NotSavedSubEntityException {
        if (message.getAuthor() == null || message.getRoom() == null) {
            throw new NotSavedSubEntityException("Author or room cannot be null.");
        }
        if (message.getAuthor().getUserId() == null || message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Author ID or Room ID is missing.");
        }

        String query = "INSERT INTO messages (author_id, room_id, text, created_at) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, message.getAuthor().getUserId());
            stmt.setLong(2, message.getRoom().getId());
            stmt.setString(3, message.getText());
            stmt.setTimestamp(4, Timestamp.valueOf(message.getCreatedAt()));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    message.setId(rs.getLong(1));
                    System.out.println("Message saved with ID: " + message.getId());
                } else {
                    throw new NotSavedSubEntityException("Failed to save message.");
                }
            }
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("SQL error occurred while saving the message.");
        }
    }
}

package chat.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public  Optional<Message> findById(Long id) {
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
                    User author = new User(rs.getInt("author_id"), rs.getString("author_login"),
                            rs.getString("author_password"));

                    Chatroom room = new Chatroom(rs.getInt("room_id"), rs.getString("room_name"));
                    Message message = new Message(
                            rs.getInt("id"),
                            // Populate author and room objects as necessary
                            author,
                            room,
                            rs.getString("text"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                            );
                    return Optional.of(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}

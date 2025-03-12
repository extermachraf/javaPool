package repo.repositories.findall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import repo.repositories.Chatroom;
import repo.repositories.User;
import repo.repositories.exeptions.NotSavedSubEntityException;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) throws NotSavedSubEntityException {
        List<User> users = new ArrayList<>();

        String query = """
        WITH PaginatedUsers AS (
            SELECT id, login, password
            FROM users
            ORDER BY id
            LIMIT ? OFFSET ?
        )
        SELECT pu.id AS user_id, pu.login, pu.password,
               cr.id AS created_room_id, cr.name AS created_room_name,
               sr.room_id AS socializing_room_id, sr_name.name AS socializing_room_name
        FROM PaginatedUsers pu
        LEFT JOIN chatrooms cr ON pu.id = cr.owner_id
        LEFT JOIN (
            SELECT DISTINCT author_id, room_id
            FROM messages
        ) sr ON pu.id = sr.author_id
        LEFT JOIN chatrooms sr_name ON sr.room_id = sr_name.id
        ORDER BY pu.id;
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the pagination parameters
            stmt.setInt(1, size); // LIMIT
            stmt.setInt(2, page * size); // OFFSET

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                Map<Long, User> userMap = new HashMap<>();

                while (rs.next()) {
                    long userId = rs.getLong("user_id");

                    // If the user hasn't been added yet, create a new User object
                    User user = userMap.get(userId);
                    if (user == null) {
                        user = new User(userId, rs.getString("login"), rs.getString("password"),
                                new ArrayList<>(), new ArrayList<>());
                        userMap.put(userId, user);
                        users.add(user);
                    }

                    // Handle created room
                    long createdRoomId = rs.getLong("created_room_id");
                    if (!rs.wasNull()) {
                        String createdRoomName = rs.getString("created_room_name");

                        // Check if this room is already in the list to avoid duplicates
                        boolean createdRoomExists = false;
                        for (Chatroom existingRoom : user.getCreatedRooms()) {
                            if (existingRoom.getId() == createdRoomId) {
                                createdRoomExists = true;
                                break;
                            }
                        }

                        if (!createdRoomExists) {
                            Chatroom createdRoom = new Chatroom(createdRoomId, createdRoomName, user, null);
                            user.getCreatedRooms().add(createdRoom);
                        }
                    }

                    // Handle socializing room
                    long socializingRoomId = rs.getLong("socializing_room_id");
                    if (!rs.wasNull()) {
                        String socializingRoomName = rs.getString("socializing_room_name");

                        // Check if this room is already in the list to avoid duplicates
                        boolean socializingRoomExists = false;
                        for (Chatroom existingRoom : user.getSocializingRooms()) {
                            if (existingRoom.getId() == socializingRoomId) {
                                socializingRoomExists = true;
                                break;
                            }
                        }

                        if (!socializingRoomExists) {
                            Chatroom socializingRoom = new Chatroom(socializingRoomId, socializingRoomName, null, null);
                            user.getSocializingRooms().add(socializingRoom);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new NotSavedSubEntityException("An error occurred while fetching users and chatrooms: " + e.getMessage());
        }

        return users;
    }
    

}


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
            WITH UserRooms AS (
                SELECT u.id AS user_id, u.login, u.password, r.id AS created_room_id, r.name AS created_room_name
                FROM users u
                LEFT JOIN chatrooms r ON u.id = r.owner_id
            ),
            SocializingRooms AS (
                SELECT DISTINCT m.author_id AS user_id, m.room_id AS socializing_room_id, c.name AS socializing_room_name
                FROM messages m
                JOIN chatrooms c ON m.room_id = c.id
            ),
            PaginatedUsers AS (
                SELECT DISTINCT user_id, login, password
                FROM UserRooms
                ORDER BY user_id
                LIMIT ? OFFSET ?
            )
            SELECT pu.user_id, pu.login, pu.password, 
                   ur.created_room_id, ur.created_room_name,
                   sr.socializing_room_id, sr.socializing_room_name
            FROM PaginatedUsers pu
            LEFT JOIN UserRooms ur ON pu.user_id = ur.user_id
            LEFT JOIN SocializingRooms sr ON pu.user_id = sr.user_id
            ORDER BY pu.user_id;
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
                    }
    
                    // Handle created room
                    long createdRoomId = rs.getLong("created_room_id");
                    if (!rs.wasNull()) {
                        Chatroom createdRoom = new Chatroom(createdRoomId, rs.getString("created_room_name"), user, null);
                        if (!user.getCreatedRooms().contains(createdRoom)) {
                            user.getCreatedRooms().add(createdRoom);
                        }
                    }
    
                    // Handle socializing room
                    long socializingRoomId = rs.getLong("socializing_room_id");
                    if (!rs.wasNull()) {
                        Chatroom socializingRoom = new Chatroom(socializingRoomId, rs.getString("socializing_room_name"), null, null);
                        if (!user.getSocializingRooms().contains(socializingRoom)) {
                            user.getSocializingRooms().add(socializingRoom);
                        }
                    }
                }
    
                // Add all users to the list
                users.addAll(userMap.values());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new NotSavedSubEntityException("An error occurred while fetching users and chatrooms.");
        }
    
        return users;
    }
    

}


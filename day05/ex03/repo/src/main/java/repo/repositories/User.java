package repo.repositories;

import java.util.List;
import java.util.Objects;

public class User {
    private Long userId;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializingRooms;

    public User(Long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> socializingRooms) {
        this.userId = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.socializingRooms = socializingRooms;
    }

    // geters
    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public List<Chatroom> getSocializingRooms() {
        return socializingRooms;
    }

    // seters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void setSocializingRooms(List<Chatroom> socializingRooms) {
        this.socializingRooms = socializingRooms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.login, this.createdRooms, this.socializingRooms);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;

        return Objects.equals(this.userId, other.userId) &&
                Objects.equals(this.login, other.login) &&
                Objects.equals(this.password, other.password) &&
                Objects.equals(this.createdRooms, other.createdRooms) &&
                Objects.equals(this.socializingRooms, other.socializingRooms);
    }

    @Override
    public String toString() {
        // Check for null or empty lists and handle accordingly
        String createdRoomNames = createdRooms == null || createdRooms.isEmpty()
                ? "None"
                : createdRooms.stream().map(Chatroom::getName).toList().toString();
    
        String socializingRoomNames = socializingRooms == null || socializingRooms.isEmpty()
                ? "None"
                : socializingRooms.stream().map(Chatroom::getName).toList().toString();
    
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createdRoomNames +
                ", socializingRooms=" + socializingRoomNames +
                '}' + "\n";
    }
}

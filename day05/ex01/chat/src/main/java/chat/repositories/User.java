package chat.repositories;

import java.util.List;
import java.util.Objects;

public class User {
    private int userId;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializingRooms;

    public User(int id, String login, String password) {
        this.userId = id;
        this.login = login;
        this.password = password;
    }

    // geters
    public int getUserId() {
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
    public void setUserId(int userId) {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + userId;
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((createdRooms == null) ? 0 : createdRooms.hashCode());
        result = prime * result + ((socializingRooms == null) ? 0 : socializingRooms.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User other = (User) obj;

        return userId == other.userId &&
                Objects.equals(login, other.login) &&
                Objects.equals(password, other.password) &&
                Objects.equals(createdRooms, other.createdRooms) &&
                Objects.equals(socializingRooms, other.socializingRooms);
    }

    @Override
    public String toString() {
        return "{id=" + userId + ",login=\"" + login + "\",password=\"" + password + "\",createdRooms=" + createdRooms + ",rooms=" + socializingRooms + "}";
    }
}

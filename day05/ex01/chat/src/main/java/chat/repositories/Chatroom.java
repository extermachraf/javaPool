package chat.repositories;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private int id;
    private String name;
    private User owner;
    private List<Message> messages;

    // constructor
    public Chatroom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    // override equals
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Chatroom chat = (Chatroom) obj;
        return this.id == chat.id && Objects.equals(this.name, chat.name)
                && Objects.equals(this.owner, chat.owner) && Objects.equals(this.messages, chat.messages);
    }

    // overridr hashCode methode
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.owner, this.messages);
    }

    // override the toString methode

    @Override
    public String toString() {
        return "{id=" + id + ",name=\"" + name + "\",creator=" + owner + ",messages=" + messages + "}";
    }

}

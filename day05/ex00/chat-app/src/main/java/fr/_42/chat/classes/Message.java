package fr._42.chat.classes;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private int id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime createdAt;

    private Message(int id, User author, Chatroom room, String text, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Message(User author, Chatroom room, String text, LocalDateTime createdAt) {
        this.author = author;
        this.room = room;
        this.text = text;
        this.createdAt = createdAt;
    }

    // geters
    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // seters
    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // overriding the equal methode for message class
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Message message = (Message) obj;
        return this.id == message.id &&
                this.author.equals(message.author) &&
                this.room.equals(message.room) &&
                this.text.equals(message.text) &&
                this.createdAt.equals(message.createdAt);
    }

    // overiding the hashcode methode
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.author, this.room, this.text, this.createdAt);
    }

    // overiding toString methode
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author +
                ", room=" + room +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

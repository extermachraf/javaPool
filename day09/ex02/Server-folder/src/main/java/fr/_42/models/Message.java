package fr._42.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    Long id;
    User sender;
    String text;
    Chatroom chatroom;
    LocalDateTime timestamp;

    public Message(User sender, String text, LocalDateTime timestamp) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }
    public Message(User sender, String text) {
        this.sender = sender;
        this.text = text;
        this.timestamp = LocalDateTime.now();
    }
}

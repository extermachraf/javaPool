package fr._42.models;

import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor @NoArgsConstructor
@ToString @EqualsAndHashCode @Getter @Setter
public class Message {
    Long id;
    Long sender;
    String message_text;
    Long room_id;
    Timestamp timestamp;
    String senderUsername;
}

package fr._42.models;

import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor @NoArgsConstructor
@ToString @EqualsAndHashCode @Getter @Setter
public class Message {
    Long id;
    Long sender;
    String message_text;
    Timestamp timestamp;
}

package fr._42.models;

import lombok.*;

import java.sql.Timestamp;

@Setter @Getter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class Chatroom {
    Long id;
    String name;
    User creator;
    Timestamp created_at;
}

package fr._42.models;

import lombok.*;

@AllArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode
public class User {
    Long id;
    String username;
    String password;
}

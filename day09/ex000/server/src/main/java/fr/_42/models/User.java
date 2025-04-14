package fr._42.models;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @EqualsAndHashCode
public class User {
    Long id;
    String username;
    String password;

}

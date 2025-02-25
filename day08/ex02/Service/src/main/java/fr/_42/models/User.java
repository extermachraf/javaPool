package fr._42.models;

import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@AllArgsConstructor
public class User {
    Long id;
    String email;
    String password;

    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }

}

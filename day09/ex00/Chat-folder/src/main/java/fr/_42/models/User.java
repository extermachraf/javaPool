package fr._42.models;

import lombok.*;

import javax.management.ConstructorParameters;
import java.io.Serializable;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class User implements Serializable {
    Long id;
    String fullName;
    String password;
    public User(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }
}

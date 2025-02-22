package fr._42.models;
import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class User {
    private Long id;
    private String fullName;
    private String email;


    public User(Long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }
    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}

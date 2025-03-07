package fr._42.dtos;

import lombok.*;

@Setter @Getter @ToString @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(callSuper = false)
public class AuthDTO extends BaseMessage {
    private String username;
    private String password;
}

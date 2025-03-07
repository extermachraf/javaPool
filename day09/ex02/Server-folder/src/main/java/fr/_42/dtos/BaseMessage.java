package fr._42.dtos;

import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode
public class BaseMessage {
    private String type;
}

package fr._42.jsonMessages;

import fr._42.models.User;
import lombok.*;

@AllArgsConstructor
@Getter @Setter @EqualsAndHashCode @ToString
public class Instruction {
    String message;
    Long sender_id;

    public Instruction(){}
    public Instruction(String message) {
        this.message = message;
    }
}

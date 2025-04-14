package fr._42.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @ToString
public class Instruction {
    String message;
    Long sender_id;

    public Instruction() {}
    public Instruction(String message) {
        this.message = message;
    }
}

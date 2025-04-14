package fr._42.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;

@Getter @Setter @NoArgsConstructor
public class ChatMessage {
    String message;
    Long sender_id;
    long room_id;
}

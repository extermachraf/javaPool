package fr._42.jsonMessages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MessageSerialization {
    private Long id;
    private String username;
    private String message;
    private String type = "CHAT_MESSAGE";  // To distinguish from other message types
}
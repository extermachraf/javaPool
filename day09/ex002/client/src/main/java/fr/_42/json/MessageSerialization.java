package fr._42.json;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MessageSerialization {
    private Long id;
    private String username;
    private String message;
    private String type = "CHAT_MESSAGE";  // To distinguish from other message types
} 
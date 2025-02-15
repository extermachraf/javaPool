package fr._42.tables;


import fr._42.annotations.OrmColumn;
import fr._42.annotations.OrmColumnId;
import fr._42.annotations.OrmEntity;

@OrmEntity(table="messages")
public class Messages {
    @OrmColumnId
    private Long id;

    @OrmColumn(name="text", length = 500)
    private String message;

    @OrmColumn(name="sender")
    private String sender;

    @OrmColumn(name="receiver")
    private String receiver;


    // constructors
    public Messages() {}
    public Messages(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

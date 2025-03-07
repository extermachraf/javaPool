package fr._42.repositpries.message;

import fr._42.models.Chatroom;
import fr._42.models.Message;

import java.util.List;

public interface MessageRepository {
    void save(Message message) throws MessageRepoExeption;
    List<Message> findRoomMessages(Chatroom chatroom);
}

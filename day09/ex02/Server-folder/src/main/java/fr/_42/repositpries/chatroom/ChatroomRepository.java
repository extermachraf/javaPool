package fr._42.repositpries.chatroom;

import fr._42.models.Chatroom;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository {
    Chatroom create(String name, Long createdBy);
    Optional<Chatroom> findById(Long id);
    List<Chatroom> findAll();
    void delete(Long chatroomId);
}

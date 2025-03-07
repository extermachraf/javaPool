package fr._42.repositpries.message;

import fr._42.models.Message;

public interface MessageRepository {
    void save(Message message) throws MessageRepoExeption;
}

package repo.repositories;

import java.util.Optional;

import repo.repositories.exeptions.NotSavedSubEntityException;

public interface MessagesRepository {
    Optional<Message> findById(Long id);
    void  save(Message message) throws NotSavedSubEntityException;
}

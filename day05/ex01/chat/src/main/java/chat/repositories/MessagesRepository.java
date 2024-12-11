package chat.repositories;

import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id);
}

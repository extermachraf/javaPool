package fr._42.repositories.messages;

import fr._42.models.Message;

import java.util.function.Supplier;

public interface MessageRepository {
    Message createMessage(Supplier<Message> messageSupplier);
}

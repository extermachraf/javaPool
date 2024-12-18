package repo.repositories.exeptions;

public class NotSavedSubEntityException extends RuntimeException {

    // Constructor that takes a message
    public NotSavedSubEntityException(String message) {
        super(message);
    }
}


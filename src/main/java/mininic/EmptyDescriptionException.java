package mininic;

/**
 * Exception thrown when a task description is empty.
 */
public class EmptyDescriptionException extends RuntimeException {
    public EmptyDescriptionException(String message) {
        super(message);
    }
}

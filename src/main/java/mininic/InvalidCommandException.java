package mininic;

/**
 * Exception thrown when an invalid command is received.
 */
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super(message);
    }
}

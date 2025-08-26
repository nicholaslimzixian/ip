package mininic;

/**
 * Exception thrown when an unknown command is received.
 */
public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String message) {
        super(message);
    }
}

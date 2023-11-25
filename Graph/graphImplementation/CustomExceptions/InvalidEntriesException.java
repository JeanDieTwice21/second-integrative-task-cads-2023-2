package graphImplementation.CustomExceptions;

public class InvalidEntriesException extends RuntimeException {
    public InvalidEntriesException(String message) {
        super(message);
    }
}

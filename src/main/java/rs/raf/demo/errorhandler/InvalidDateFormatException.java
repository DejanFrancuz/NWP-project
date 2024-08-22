package rs.raf.demo.errorhandler;

public class InvalidDateFormatException extends RuntimeException {

    // Konstruktor bez argumenata
    public InvalidDateFormatException() {
        super();
    }

    // Konstruktor sa porukom
    public InvalidDateFormatException(String message) {
        super(message);
    }

    // Konstruktor sa porukom i uzrokom
    public InvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    // Konstruktor sa uzrokom
    public InvalidDateFormatException(Throwable cause) {
        super(cause);
    }
}
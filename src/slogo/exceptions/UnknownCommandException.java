package slogo.exceptions;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String message, Object ... values) {
        super(String.format(message, values));
    }

    public UnknownCommandException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }
}

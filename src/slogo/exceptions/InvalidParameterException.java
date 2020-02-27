package slogo.exceptions;

public class InvalidParameterException extends Exception {
    public InvalidParameterException(String message, Object ... values) {
        super(String.format(message, values));
    }

    public InvalidParameterException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }
}
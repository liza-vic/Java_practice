package by.bsu.practice.collections.exception;

public class NullStringException extends Exception{

    public NullStringException() {
    }

    public NullStringException(String message) {
        super(message);
    }

    public NullStringException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullStringException(Throwable cause) {
        super(cause);
    }
}

package net.example.ftpimitation.exception;

public class UserNotExistException extends Throwable {
    public UserNotExistException() {
        super();
    }

    public UserNotExistException(Throwable cause) {
        super(cause);
    }

    public UserNotExistException(String message) {
        super(message);

    }
}

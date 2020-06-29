package net.example.ftpimitation.exception;

public class IncorrectPasswordException extends Throwable {

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(Throwable e) {
        super(e);
    }

}

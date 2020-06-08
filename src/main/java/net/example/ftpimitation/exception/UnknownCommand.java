package net.example.ftpimitation.exception;

public class UnknownCommand extends RuntimeException {
    public UnknownCommand(String message) {
        super(message);
    }
}

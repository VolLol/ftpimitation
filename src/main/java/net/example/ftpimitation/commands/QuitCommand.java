package net.example.ftpimitation.commands;

public class QuitCommand implements Command {

    @Override
    public CommandType getCommandType() {
        return CommandType.QUIT;
    }
}

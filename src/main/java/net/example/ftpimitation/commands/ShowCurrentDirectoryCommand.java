package net.example.ftpimitation.commands;

public class ShowCurrentDirectoryCommand implements Command {
    @Override
    public CommandType getCommandType() {
        return CommandType.SCD;
    }
}

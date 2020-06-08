package net.example.ftpimitation.commands;

import lombok.Getter;
import lombok.Setter;

public class ChangeDirectoryCommand implements Command {

    @Getter
    @Setter
    private String directory;

    public ChangeDirectoryCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.CD;
    }
}

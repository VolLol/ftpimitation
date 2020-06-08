package net.example.ftpimitation.commands;

import lombok.Getter;
import lombok.Setter;

public class ListDirectoryCommand implements Command {

    @Setter
    @Getter
    private String directory;

    public ListDirectoryCommand(String directory) {
        this.directory = directory;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.LIST;
    }
}

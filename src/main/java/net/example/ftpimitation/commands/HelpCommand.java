package net.example.ftpimitation.commands;

import lombok.Getter;

public class HelpCommand implements Command {

    @Getter
    private String command;

    public HelpCommand(String command) {
        this.command = command;
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.HELP;
    }
}

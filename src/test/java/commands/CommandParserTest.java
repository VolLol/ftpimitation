package commands;

import net.example.ftpimitation.utils.CommandParser;
import net.example.ftpimitation.commands.*;
import org.junit.Assert;
import org.junit.Test;

public class CommandParserTest {


    @Test
    public void correctHelpCommandWithoutArgs() {
        HelpCommand helpCommand = new HelpCommand(null);
        CommandParser commandParser = new CommandParser();
        Command exsiting = commandParser.parser("help");


        Assert.assertEquals(helpCommand.getCommandType(), exsiting.getCommandType());
    }

    @Test
    public void correctHelpCommandWithArgs() {
        HelpCommand helpCommand = new HelpCommand("quit");
        CommandParser commandParser = new CommandParser();
        Command exsiting = commandParser.parser("help -quit");

        Assert.assertEquals(helpCommand.getCommandType(), exsiting.getCommandType());
    }

    @Test
    public void correctShowCurrentDirectory() {
        ShowCurrentDirectoryCommand showCurrentDirectoryCommand = new ShowCurrentDirectoryCommand();
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("pwd");

        Assert.assertEquals(showCurrentDirectoryCommand.getCommandType(), exciting.getCommandType());
    }

    @Test
    public void correctListCommandWithoutDirectory() {
        ListDirectoryCommand listDirectoryCommand = new ListDirectoryCommand(null);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("ls");

        Assert.assertEquals(listDirectoryCommand.getCommandType(), exciting.getCommandType());
    }

    @Test
    public void correctListcommandWithDirectory() {
        ListDirectoryCommand listDirectoryCommand = new ListDirectoryCommand(null);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("ls //build");

        Assert.assertEquals(listDirectoryCommand.getCommandType(), exciting.getCommandType());
    }

    @Test
    public void correctChangeDirectoryCommand() {
        String directory = "\\build";
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand(directory);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("cd /build");

        Assert.assertEquals(exciting.getCommandType(), changeDirectoryCommand.getCommandType());
    }

    @Test
    public void correctChangeDirectoryCommandWithoutArguments() {
        String directory = "directory";
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand(directory);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("cd");

        Assert.assertEquals(exciting.getCommandType(), changeDirectoryCommand.getCommandType());
    }

    @Test
    public void correctChangeDirectoryCommandWithTwoDots() {
        String directory = "directory";
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand(directory);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("cd ..");

        Assert.assertEquals(exciting.getCommandType(), changeDirectoryCommand.getCommandType());
    }

    @Test
    public void correctChangeDirectoryCommandWithTwoDotsAndSlash() {
        String directory = "directory";
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand(directory);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("cd ../../");

        Assert.assertEquals(exciting.getCommandType(), changeDirectoryCommand.getCommandType());
    }

    @Test
    public void correctChangeDirectoryCommandWithFolderName() {
        String directory = "directory";
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand(directory);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("cd tmp");

        Assert.assertEquals(exciting.getCommandType(), changeDirectoryCommand.getCommandType());
    }

    @Test
    public void correctChangeDirectoryCommandWithFolderNameAndSlash() {
        String directory = "directory";
        ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand(directory);
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("cd /tmp/base");

        Assert.assertEquals(exciting.getCommandType(), changeDirectoryCommand.getCommandType());
    }


    @Test
    public void correctQuitCommand() {
        QuitCommand quitCommand = new QuitCommand();
        CommandParser commandParser = new CommandParser();
        Command exciting = commandParser.parser("quit");

        Assert.assertEquals(exciting.getCommandType(), quitCommand.getCommandType());

    }
}

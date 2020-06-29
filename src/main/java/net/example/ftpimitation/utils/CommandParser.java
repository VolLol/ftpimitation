package net.example.ftpimitation.utils;

import net.example.ftpimitation.commands.*;
import net.example.ftpimitation.exception.UnknownCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    final private static Pattern quitCommandPattern = Pattern.compile("^(?i)(quit)(?-i)$");
    final private static Pattern helpCommandPattern = Pattern.compile("^(?i)(help)(?-i)($|\\s-([a-z]+))");
    final private static Pattern scdCommandPattern = Pattern.compile("^(?i)(pwd)(?-i)$");
    final private static Pattern listCommandPattern = Pattern.compile("(?i)ls(?-i)($|\\s([a-z\\/]+))");
    final private static Pattern changeDirectoryPattern = Pattern.compile("^^(?i)cd(?-i)(\\s[a-zA-Z\\/]+|\\s[\\.\\.\\/]+|\\s\\.\\.|$)");

    public static Command parser(String line) {
        Matcher m = quitCommandPattern.matcher(line);
        if (m.matches()) {
            return new QuitCommand();
        }

        m = helpCommandPattern.matcher(line);
        if (m.matches()) {
            String command = m.group(3);
            return new HelpCommand(command);
        }

        m = scdCommandPattern.matcher(line);
        if (m.matches()) {
            return new ShowCurrentDirectoryCommand();
        }

        m = listCommandPattern.matcher(line);
        if (m.matches()) {
            String directory = m.group(2);
            return new ListDirectoryCommand(directory);
        }

        m = changeDirectoryPattern.matcher(line);
        if (m.matches()) {
            String directory = m.group(1);
            return new ChangeDirectoryCommand(directory);
        }
        throw new UnknownCommand("Unknown command");
    }
}

package net.example.ftpimitation.usecases;

import net.example.ftpimitation.SessionContext;

import java.util.ArrayList;
import java.util.List;

public class HelpUseCase {

    private final SessionContext sessionContext;

    public HelpUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }


    public List<String> execute(String command) throws NullPointerException {
        List<String> result = new ArrayList<>();
        System.out.println("[" + sessionContext.getClientIp() + "] " + "execute HelpUseCase");
        result.add("214 Help message");
        if (command == null) {
            command = "all";
            List<String> prepared = prepareTabel();
            result.addAll(prepared);
        }
        if (command.equals("quit")) {
            result.add("-This command to quiet from server");
        }
        if (command.equals("help")) {
            result.add("-This command show you information about all commands");
        }
        if (command.equals("ls")) {
            result.add("-This command show content of directory");
        }
        if (command.equals("pwd")) {
            result.add("-This command show current directory");
        }
        if (command.equals("cd")) {
            result.add("-This command change directory");
        }
        return result;
    }

    private List<String> prepareTabel() {
        List<String> table = new ArrayList<>();
        table.add("Command|   args   |description");
        table.add("------------------------------------");
        table.add("quit   |          |Quit from server");
        table.add("help   |[command] |Return information about command, args optional");
        table.add("ls     |   [dir]  |Return content of folder,args optimal");
        table.add("pwd    |          |Return current directory");
        table.add("cd     |   [dir]  |Change current directory, args required");
        return table;
    }
}

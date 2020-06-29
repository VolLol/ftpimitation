package net.example.ftpimitation.usecases;

import net.example.ftpimitation.utils.SessionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ShowCurrentDirectoryUseCase {

    private SessionContext sessionContext;

    public ShowCurrentDirectoryUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public List<String> execute() {
        System.out.println("[" + sessionContext.getClientIp() + "] " + "execute ShowCurrentUseCase");
        ArrayList<String> result = new ArrayList<>();

        Stack<String> tmpAllowedPath = sessionContext.getAllowedPath();
        String currentPath = "/";
        for (String s : tmpAllowedPath) {
            if (!s.equals("/")) {
                currentPath = currentPath + s + "/";
            }
        }
        if (currentPath.length() > 2) {
            currentPath = currentPath.substring(0, currentPath.length() - 1);
        }

        currentPath = currentPath.replace("//", "/");
        result.add("257 \"" + currentPath + "\" is current directory");
        return result;
    }
}

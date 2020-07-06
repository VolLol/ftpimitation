package net.example.ftpimitation.usecases;

import net.example.ftpimitation.utils.SessionContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class ListDirectoryUseCase {

    private final SessionContext sessionContext;

    public ListDirectoryUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public List<String> execute(String directory) {
        System.out.println("[" + sessionContext.getClientIp() + "] " + "execute ListDirectoryUseCase");
        List<String> answer = new ArrayList<>();
        String path = pathGeneration(directory, sessionContext);
        File file = new File(path);
        if (file.exists() && Objects.requireNonNull(file.listFiles()).length > 0) {
            addHead(answer);
            for (File fileFromDirectory : Objects.requireNonNull(file.listFiles())) {
                if (fileFromDirectory.isDirectory()) {
                    answer.add("dir      " + fileFromDirectory.getName());
                } else {
                    answer.add("file      " + fileFromDirectory.getName());
                }
            }
            answer.add("-----------------");
        } else {
            answer.add("This path is empty");
        }


        return answer;
    }

    private String pathGeneration(String directory, SessionContext sessionContext) {
        String path;
        Stack<String> stack = sessionContext.getAllowedPath();
        if (directory == null) {
            directory = "";
        }

        if (directory.equals("")) {
            path = sessionContext.getRootPath().toString();
            if (!stack.empty()) {
                for (String s : stack) {
                    path = path + "/" + s;
                }
            }
        } else {
            path = sessionContext.getRootPath() + directory;
        }
        return path;
    }

    private void addHead(List<String> answer) {
        answer.add("200 Command okay.");
        answer.add("-----------------");
        answer.add("Type      Name");
        answer.add("-----------------");
    }

}

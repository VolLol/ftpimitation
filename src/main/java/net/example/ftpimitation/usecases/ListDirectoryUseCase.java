package net.example.ftpimitation.usecases;

import net.example.ftpimitation.SessionContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ListDirectoryUseCase {

    private final SessionContext sessionContext;

    public ListDirectoryUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public List<String> execute(String directory) {
        System.out.println("[" + sessionContext.getClientIp() + "] " + "execute ListDirectoryUseCase");
        List<String> result = new ArrayList<>();
        String resultPath;


        if (directory == null || directory.equals("")) {
            resultPath = sessionContext.getRootPath().toString();

            Stack<String> tmpAllowedPath = sessionContext.getAllowedPath();

            for (String s : tmpAllowedPath) {
                resultPath = resultPath + "/" + s;
            }


        } else {
            resultPath = sessionContext.getRootPath() + directory;
        }

        File resultDir = new File(resultPath);
        result.add("200 Command okay.");

        if (resultDir.listFiles().length > 0) {
            result.add("Type      Name");
            for (File fileFromDirectory : resultDir.listFiles()) {
                if (fileFromDirectory.isDirectory()) {
                    result.add("dir      " + fileFromDirectory.getName());
                } else {
                    result.add("file      " + fileFromDirectory.getName());
                }


            }
        } else {
            result.add("This path is empty");
        }

        return result;
    }
}

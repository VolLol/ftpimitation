package net.example.ftpimitation.usecases;

import net.example.ftpimitation.SessionContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChangeDirectoryUseCase {


    private final SessionContext sessionContext;

    public ChangeDirectoryUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }


    public List<String> execute(String directory) {
        System.out.println("[" + sessionContext.getClientIp() + "] " + "execute ChangeDirectoryUseCase");
        List<String> result = new ArrayList<>();

        if (directory.length() > 1) {
            directory = directory.substring(1);
        }


        if (directory.equals("") || directory.equals("..")) {
            if (!sessionContext.getAllowedPath().isEmpty()) {
                sessionContext.getAllowedPath().pop();
                String resultPath = "/";

                for (String s : sessionContext.getAllowedPath()) {
                    resultPath = resultPath + s + "/";
                }

                if (resultPath.length() > 2) {
                    resultPath = resultPath.substring(0, resultPath.length() - 1);
                }
                resultPath = resultPath.replace("//", "/");
                result.add("250 CWD successful. Current directory: " + resultPath);
            } else {
                result.add("250 CWD successful. Current directory: /");
            }
        }


        if (directory.contains("../")) {
            int countLevels = 0;
            Pattern dotsAndSlash = Pattern.compile("\\.\\.\\/");
            Matcher m = dotsAndSlash.matcher(directory);

            while (m.find()) {
                countLevels++;
            }
            if (sessionContext.getAllowedPath().size() < countLevels) {
                countLevels = sessionContext.getAllowedPath().size();
            }

            for (int i = 0; i < countLevels; i++) {
                sessionContext.getAllowedPath().pop();
            }
            String resultPath = "/";

            for (String s : sessionContext.getAllowedPath()) {
                resultPath = resultPath + s + "/";
            }


            if (resultPath.length() > 2) {
                resultPath = resultPath.substring(0, resultPath.length() - 1);
            }
            resultPath = resultPath.replace("//", "/");
            result.add("250 CWD successful. Current directory: " + resultPath);


        }


        if (directory.startsWith("/")) {
            String tmpPath = sessionContext.getRootPath().toString() + directory;

            File file = new File(tmpPath);
            if (file.exists()) {
                sessionContext.getAllowedPath().clear();
                result.add("250 CWD successful. Current directory: " + directory);
                directory = directory.substring(1);
                String[] tmp = directory.split("/");
                for (String s : tmp) {
                    if (!s.equals("/")) {
                        sessionContext.getAllowedPath().push(s);
                    }
                }
            } else {
                result.add("550 CWD failed. " + directory + " Directory not found");
            }

        } else if (!directory.equals("") && !directory.contains("../") && !directory.equals("..")) {
            directory = "/" + directory;

            String tmpPath = sessionContext.getRootPath().toString();

            if (!sessionContext.getAllowedPath().empty()) {
                for (String s : sessionContext.getAllowedPath()) {
                    tmpPath = tmpPath + "/" + s;
                }
            }
            tmpPath = tmpPath + directory;
            File file = new File(tmpPath);

            if (file.exists()) {
                String currentPath = new String();

                for (String s : sessionContext.getAllowedPath()) {
                    currentPath = currentPath + "/" + s;
                }
                currentPath = currentPath + "/" + directory;
                currentPath = currentPath.replace("//", "/");
                result.add("250 CWD successful. Current directory: " + currentPath);

                String[] arrayPath = directory.split("/");


                for (String s : arrayPath) {
                    if (!s.equals("/")) {
                        sessionContext.getAllowedPath().push(s);
                    }
                }

            } else {
                result.add("550 CWD failed. " + directory + " Directory not found");
            }
        }

        return result;
    }
}
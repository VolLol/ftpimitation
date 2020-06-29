package net.example.ftpimitation.utils;


import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

public class SessionContext {

    @Getter
    private String clientIp;

    @Getter
    private Path rootPath;

    @Getter
    @Setter
    private Stack<String> allowedPath;

    public SessionContext(String clientIp) {
        this.clientIp = clientIp;
        this.rootPath = Paths.get("C:/Develop/ftpimitation/build");
        this.allowedPath = new Stack<>();
    }
}

package net.example.ftpimitation.usecases;

import net.example.ftpimitation.utils.SessionContext;

import java.util.ArrayList;
import java.util.List;


public class QuitUseCase {


    private final SessionContext sessionContext;

    public QuitUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public List<String> execute() {
        System.out.println("[" + sessionContext.getClientIp() + "] " + "execute QuitUseCase");
        ArrayList<String> result = new ArrayList<>();
        result.add("221 Goodbye, closing session ");
        return result;
    }
}

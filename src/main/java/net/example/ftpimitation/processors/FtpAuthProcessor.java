package net.example.ftpimitation.processors;

import net.example.ftpimitation.repository.UserRepository;
import net.example.ftpimitation.utils.SessionContext;
import net.example.ftpimitation.usecases.AuthUseCase;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FtpAuthProcessor {

    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private SessionContext sessionContext;

    public FtpAuthProcessor(BufferedReader socketIn, PrintWriter socketOut, SessionContext sessionContext) {
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.sessionContext = sessionContext;
    }

    public void execute() {
        System.out.println("[" + sessionContext.getClientIp() + "] client start authentication");
        socketOut.println("Welcome to ftp server!");
        socketOut.flush();
        AuthUseCase authUseCase = new AuthUseCase(sessionContext, new UserRepository(sessionContext));
        ImmutablePair<String, Boolean> answer = new ImmutablePair<>("", false);
        while (!answer.getValue()) {
            answer = authUseCase.execute
                    (receivingData("username"), receivingData("password"));
            socketOut.println(answer.getKey());
        }
        System.out.println("[" + sessionContext.getClientIp() + "] client finish authentication");
    }

    private String receivingData(String dataName) {
        String data = null;
        try {
            socketOut.println("Please enter " + dataName + ":");
            socketOut.flush();
            data = socketIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}

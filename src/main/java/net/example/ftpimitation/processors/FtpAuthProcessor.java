package net.example.ftpimitation.processors;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.usecases.AuthUseCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        AuthUseCase authUseCase = new AuthUseCase(sessionContext);
        boolean authCorrect = false;
        List<String> answer;
        while (!authCorrect) {
            answer = authUseCase.execute
                    (receivingData("username"), receivingData("password"));
            if (answer.size() == 0) {
                authCorrect = true;
            } else {
                for (String s : answer) {
                    socketOut.println(s);
                }
            }
        }
        System.out.println("[" + sessionContext.getClientIp() + "] client finish authentication");
    }


    private String receivingData(String dataName) {
        String data = null;
        try {
            socketOut.println("Write " + dataName + ":");
            socketOut.flush();
            data = socketIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}

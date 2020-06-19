package net.example.ftpimitation;

import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.UserNotExistException;
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
        while (!authCorrect) {
            try {
                List answer = authUseCase.execute(receivingData("username"), receivingData("password"));
                if (answer.size() == 2) {
                    authCorrect = true;
                }
            } catch (UserNotExistException e) {
                System.out.println("[" + sessionContext.getClientIp() + "] write incorrect username");
                socketOut.println("User not exist");
                socketOut.flush();
            } catch (IncorrectPasswordException e) {
                System.out.println("[" + sessionContext.getClientIp() + "] write incorrect password");
                socketOut.println("Incorrect password");
                socketOut.flush();
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

package net.example.ftpimitation.processors;

import net.example.ftpimitation.repository.UserRepository;
import net.example.ftpimitation.utils.SessionContext;
import net.example.ftpimitation.usecases.AuthUseCase;
import org.apache.commons.lang3.tuple.ImmutablePair;

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
        AuthUseCase authUseCase = new AuthUseCase(sessionContext, new UserRepository(sessionContext));
        ImmutablePair<List<String>, Boolean> usecaseResult = null;
        do {
            String username = receivingData("username");
            String password = receivingData("password");
            usecaseResult = authUseCase.execute(username, password);
            for (String data : usecaseResult.left) {
                socketOut.println(data);
            }
            socketOut.flush();
        } while (!usecaseResult.right);
        System.out.println("[" + sessionContext.getClientIp() + "] client successful authentication");
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

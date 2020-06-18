package net.example.ftpimitation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FtpAuthProcessor {

    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private SessionContext sessionContext;
    private UserEntity userEntity;
    private UserRepository userRepository;
    private String usernameFromClient;
    private String cleanPasswordFromClient;
    private Boolean successfulAuth = false;

    public FtpAuthProcessor(BufferedReader socketIn, PrintWriter socketOut, SessionContext sessionContext) {
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.sessionContext = sessionContext;
        this.userEntity = new UserEntity();
        this.userRepository = new UserRepository();
    }


    public void execute() {
        System.out.println("[" + sessionContext.getClientIp() + "] client start authentication");

        socketOut.println("Welcome to ftp server!");
        socketOut.flush();

        while (!successfulAuth) {
            receivingDataFromClient();
            checkingUsernameAndPassword();
        }

        System.out.println("[" + sessionContext.getClientIp() + "] client finish authentication");
    }


    private void receivingDataFromClient() {
        try {
            socketOut.println("Write username:");
            socketOut.flush();
            usernameFromClient = socketIn.readLine();
            socketOut.println("Write password:");
            socketOut.flush();
            cleanPasswordFromClient = socketIn.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkingUsernameAndPassword() {
        try {
            UserEntity user = userRepository.findByUsername(usernameFromClient);

            if (user.getCleanPassword().equals(cleanPasswordFromClient)) {
                socketOut.println("Connect successful");
                successfulAuth = true;
                socketOut.flush();
            } else {
                System.out.println("[" + sessionContext.getClientIp() + "] client write incorrect password");
                socketOut.println("Password incorrect");
                socketOut.flush();
                successfulAuth = false;
            }

        } catch (NullPointerException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] client write incorrect username");
            socketOut.println("Incorrect username");
            socketOut.flush();
            successfulAuth = false;
        }
    }

}

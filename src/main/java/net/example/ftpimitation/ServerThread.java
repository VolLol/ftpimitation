package net.example.ftpimitation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private SessionContext sessionContext;
    private String clientIp;

    public ServerThread(Socket clientSocket) throws IOException {
        socketIn = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
        socketOut = new PrintWriter(clientSocket.getOutputStream(), false);
        clientIp = clientSocket.getRemoteSocketAddress().toString();
        sessionContext = new SessionContext(clientIp);
    }


    public void run() {
        System.out.println("[" + clientIp + "] thread start works");

        FtpAuthProcessor ftpAuthProcessor = new FtpAuthProcessor(socketIn, socketOut, sessionContext);
        ftpAuthProcessor.execute();

        FtpProcessor ftpProcessor = new FtpProcessor(socketIn, socketOut, sessionContext);
        try {
            ftpProcessor.execute();
        } catch (IOException e) {
            System.out.println("[" + clientIp + "] finish working");
        } finally {
            disconnect();
        }

    }

    private void disconnect() {
        try {
            System.out.println("[" + clientIp + "] disconnect");
            socketOut.close();
            socketIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }

    }
}

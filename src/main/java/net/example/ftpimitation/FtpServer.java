package net.example.ftpimitation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpServer {

    public static void main(String[] args) throws Exception {

        int serverPort = 2101;
        ServerSocket serverSocket = new ServerSocket(serverPort);

        while (true) {
            Socket clientSocket = null;
            String clientIp = null;
            try {
                clientSocket = serverSocket.accept();
                clientIp = clientSocket.getRemoteSocketAddress().toString();
                System.out.println("[" + clientIp + "] connected");

                BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter socketOut = new PrintWriter(clientSocket.getOutputStream(), false);

                SessionContext sessionContext = new SessionContext(clientIp);
                FtpProcessor ftpProcessor = new FtpProcessor(socketIn, socketOut, sessionContext);
                ftpProcessor.execute();

            } catch (Exception e) {
                if (clientSocket != null) {
                    System.out.println("[" + clientIp + "] " + e.getMessage());
                    clientSocket.close();
                }

            }

        }

    }
}

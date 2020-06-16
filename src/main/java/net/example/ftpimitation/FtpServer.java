package net.example.ftpimitation;

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

                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.setName(clientIp);
                serverThread.start();

            } catch (Exception e) {
                if (clientSocket != null) {
                    System.out.println("[" + clientIp + "] " + e.getMessage());
                    clientSocket.close();
                }

            }

        }

    }
}

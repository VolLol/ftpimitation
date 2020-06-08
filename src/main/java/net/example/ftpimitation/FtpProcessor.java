package net.example.ftpimitation;

import net.example.ftpimitation.commands.*;
import net.example.ftpimitation.usecases.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FtpProcessor {


    private final BufferedReader socketIn;
    private final PrintWriter socketOut;
    private final SessionContext sessionContext;
    private final QuitUseCase quitUseCase;
    private final HelpUseCase helpUseCase;
    private final ShowCurrentDirectoryUseCase showCurrentDirectoryUseCase;
    private final ListDirectoryUseCase listDirectoryUseCase;
    private final ChangeDirectoryUseCase changeDirectoryUseCase;

    public FtpProcessor(BufferedReader socketIn, PrintWriter socketOut, SessionContext sessionContext) {
        this.socketIn = socketIn;
        this.socketOut = socketOut;
        this.sessionContext = sessionContext;
        this.quitUseCase = new QuitUseCase(sessionContext);
        this.helpUseCase = new HelpUseCase(sessionContext);
        this.showCurrentDirectoryUseCase = new ShowCurrentDirectoryUseCase(sessionContext);
        this.listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        this.changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
    }

    public void execute() throws IOException {
        ArrayList<String> outBuffer = new ArrayList<>();
        String readLine;

        socketOut.println("220 FTP Server ready...");
        socketOut.flush();
        System.out.println("[" + sessionContext.getClientIp() + "]" + " ready for commands");
        while ((readLine = socketIn.readLine()) != null) {
            try {
                System.out.println("[" + sessionContext.getClientIp() + "]" + " user send data = " + readLine);
                outBuffer.clear();
                Command command = CommandParser.parser(readLine);

                if (command instanceof QuitCommand) {
                    outBuffer.addAll(quitUseCase.execute());
                }

                if (command instanceof HelpCommand) {
                    outBuffer.addAll(helpUseCase.execute(((HelpCommand) command).getCommand()));
                }

                if (command instanceof ShowCurrentDirectoryCommand) {
                    outBuffer.addAll(showCurrentDirectoryUseCase.execute());
                }

                if (command instanceof ListDirectoryCommand) {
                    outBuffer.addAll(listDirectoryUseCase.execute(((ListDirectoryCommand) command).getDirectory()));
                }

                if (command instanceof ChangeDirectoryCommand) {
                    outBuffer.addAll(changeDirectoryUseCase.execute(((ChangeDirectoryCommand) command).getDirectory()));
                }
                for (String data : outBuffer) {
                    socketOut.println(data);
                    if (command instanceof QuitCommand) {
                        socketIn.close();
                        socketOut.close();
                    }
                }


            } catch (Exception e) {
                System.err.println("[" + sessionContext.getClientIp() + "]" + " ERR unknown command ");
                socketOut.println("-ERR unknown command");
            }
            socketOut.flush();

        }


    }
}

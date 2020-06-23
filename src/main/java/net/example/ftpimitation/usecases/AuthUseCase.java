package net.example.ftpimitation.usecases;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.repository.UserRepository;
import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.UserNotExistException;

import java.util.ArrayList;
import java.util.List;

public class AuthUseCase {

    private SessionContext sessionContext;
    private UserRepository userRepository;

    public AuthUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
        this.userRepository = new UserRepository(sessionContext);
    }

    public List<String> execute(String username, String password) {
        List<String> answer = new ArrayList<>();
        System.out.println("[" + sessionContext.getClientIp() + "] execute AuthUseCase");

        try {
            userRepository.checkUsernameAndPassword(username, password);
        } catch (UserNotExistException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] write incorrect username");
            answer.add("User not exist");
        } catch (IncorrectPasswordException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] write incorrect password");
            answer.add("Incorrect password");
        }

        return answer;
    }
}

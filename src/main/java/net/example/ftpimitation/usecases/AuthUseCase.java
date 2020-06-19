package net.example.ftpimitation.usecases;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.UserEntity;
import net.example.ftpimitation.UserRepository;
import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.UserNotExistException;

import java.util.ArrayList;
import java.util.List;

public class AuthUseCase {

    private SessionContext sessionContext;
    private UserRepository userRepository;

    public AuthUseCase(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
        this.userRepository = new UserRepository();
    }

    public List execute(String username, String password) throws UserNotExistException, IncorrectPasswordException {
        List<String> answer = new ArrayList<>();
        System.out.println("[" + sessionContext.getClientIp() + "] execute AuthUseCase");

        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println("[" + sessionContext.getClientIp() + "] write correct username");
            answer.add("");
        } else {
            throw new UserNotExistException();
        }

        if (user.getCleanPassword().equals(password)) {
            System.out.println("[" + sessionContext.getClientIp() + "] write correct password");
            answer.add("");
        } else {
            throw new IncorrectPasswordException();
        }
        return answer;
    }
}

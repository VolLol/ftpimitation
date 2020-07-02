package net.example.ftpimitation.usecases;

import net.example.ftpimitation.entities.UserEntity;
import net.example.ftpimitation.exception.ProblemConnectionToDatabaseException;
import net.example.ftpimitation.repository.UserRepository;
import net.example.ftpimitation.utils.SessionContext;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;

public class AuthUseCase {

    private SessionContext sessionContext;
    private UserRepository userRepository;

    public AuthUseCase(SessionContext sessionContext, UserRepository userRepository) {
        this.sessionContext = sessionContext;
        this.userRepository = userRepository;
    }

    public ImmutablePair<List<String>, Boolean> execute(String username, String password) {
        System.out.println("[" + sessionContext.getClientIp() + "] execute AuthUseCase");
        ArrayList<String> answer = new ArrayList<>();
        try {
            UserEntity userFromDb = userRepository.findUserByUsername(username);
            if (userFromDb == null || !userFromDb.getCleanPassword().equals(password)) {
                answer.add("Incorrect user or password");
                return new ImmutablePair<>(answer, false);
            }
            answer.add("Successful authentication");
            return new ImmutablePair<>(answer, true);
        } catch (ProblemConnectionToDatabaseException e) {
            System.out.println(("[" + sessionContext.getClientIp() + "] problem connection to database"));
            answer.add("Incorrect user or password");
        }
        return new ImmutablePair<>(answer, false);
    }
}

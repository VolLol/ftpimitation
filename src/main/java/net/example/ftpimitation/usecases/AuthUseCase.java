package net.example.ftpimitation.usecases;

import net.example.ftpimitation.entities.UserEntity;
import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.ProblemConnectionToDatabaseException;
import net.example.ftpimitation.exception.UserNotExistException;
import net.example.ftpimitation.repository.UserRepository;
import net.example.ftpimitation.utils.SessionContext;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class AuthUseCase {

    private SessionContext sessionContext;
    private UserRepository userRepository;

    public AuthUseCase(SessionContext sessionContext, UserRepository userRepository) {
        this.sessionContext = sessionContext;
        this.userRepository = userRepository;
    }

    public ImmutablePair<String, Boolean> execute(String username, String password) {
        System.out.println("[" + sessionContext.getClientIp() + "] execute AuthUseCase");
        ImmutablePair<String, Boolean> answer = null;
        try {
            UserEntity userFromDb = userRepository.findUserByUsername(username);
            if (userFromDb == null) {
                throw new UserNotExistException("User not exist");
            }
            if (!userFromDb.getCleanPassword().equals(password)) {
                throw new IncorrectPasswordException("Incorrect password");
            } else {
                answer = new ImmutablePair<>("Successful authentication", true);
            }

        } catch (UserNotExistException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] write incorrect username");
            answer = new ImmutablePair<>("Incorrect username", false);
        } catch (IncorrectPasswordException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] write incorrect password");
            answer = new ImmutablePair<>("Incorrect password", false);
        } catch (ProblemConnectionToDatabaseException e) {
            System.out.println(("[" + sessionContext.getClientIp() + "] Problem with database"));
        }
        return answer;
    }
}

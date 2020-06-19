package UseCase;

import lombok.SneakyThrows;
import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.UserNotExistException;
import net.example.ftpimitation.usecases.AuthUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AuthUseCaseTest {

    @SneakyThrows
    @Test
    public void correctUsernameAndPassword() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        AuthUseCase authUseCase = new AuthUseCase(sessionContext);

        List result = authUseCase.execute("user1", "pass1");

        Assert.assertEquals(2, result.size());
    }

    @SneakyThrows
    @Test(expected = UserNotExistException.class)
    public void incorrectUsername() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        AuthUseCase authUseCase = new AuthUseCase(sessionContext);

        authUseCase.execute("jjjjjjj", "pass1");
    }


    @SneakyThrows
    @Test(expected = IncorrectPasswordException.class)
    public void incorrectPassword() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        AuthUseCase authUseCase = new AuthUseCase(sessionContext);

        authUseCase.execute("user2", "ksdjvblakjsbnv;/QWMKL");
    }

}

package UseCase;

import lombok.SneakyThrows;
import net.example.ftpimitation.SessionContext;
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

        Assert.assertEquals(0, result.size());

    }

    @SneakyThrows
    @Test
    public void incorrectUsername() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        AuthUseCase authUseCase = new AuthUseCase(sessionContext);

        List result = authUseCase.execute("jjjjjjj", "pass1");

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("User not exist", result.get(0));
    }


    @Test
    public void incorrectPassword() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        AuthUseCase authUseCase = new AuthUseCase(sessionContext);

        List result = authUseCase.execute("user2", "ksdjvblakjsbnv;/QWMKL");

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Incorrect password", result.get(0));

    }

}

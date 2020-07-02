package usecases;

import net.example.ftpimitation.utils.SessionContext;
import net.example.ftpimitation.usecases.QuitUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class QuitUseCaseTest {

    @Test
    public void correctQuit() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        QuitUseCase quitUseCase = new QuitUseCase(sessionContext);

        List answer = quitUseCase.execute();

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("221 Goodbye, closing session ", answer.get(0));
    }
}

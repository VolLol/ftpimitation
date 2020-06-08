package UseCase;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.usecases.ShowCurrentDirectoryUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ShowCurrentDirectoryUseCaseTest {

    @Test
    public void correctSCD() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        ShowCurrentDirectoryUseCase showCurrentDirectoryUseCase = new ShowCurrentDirectoryUseCase(sessionContext);
        List<String> answer = showCurrentDirectoryUseCase.execute();

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("257 \"/\" is current directory", answer.get(0));

    }





}

package UseCase;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.usecases.HelpUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HelpUseCaseTest {

    @Test
    public void correctAllCommands() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        HelpUseCase helpUseCase = new HelpUseCase(sessionContext);
        List<String> answer = helpUseCase.execute(null);

        Assert.assertEquals("214 Help message", answer.get(0));
        Assert.assertEquals("Command|   args   |description", answer.get(1));
        Assert.assertEquals("------------------------------------", answer.get(2));
    }

    @Test
    public void correctQuitCommand() {
        String clientIp = "clientIp";
        SessionContext sessionContext = new SessionContext(clientIp);
        HelpUseCase helpUseCase = new HelpUseCase(sessionContext);
        List<String> answer = helpUseCase.execute("quit");

        Assert.assertEquals("214 Help message", answer.get(0));
        Assert.assertEquals("-This command to quiet from server", answer.get(1));
    }
}

package usecases;

import net.example.ftpimitation.utils.SessionContext;
import net.example.ftpimitation.usecases.ChangeDirectoryUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ChangeDirectoryUseCaseTest {


    @Test
    public void correctChangingWithoutArgumentsAndTwoLevelsDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("tmp");
        sessionContext.getAllowedPath().add("compileJava");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute("");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /", answer.get(0));

    }

    @Test
    public void correctChangingWithoutArgumentsAndOneLevelDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("classes");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute("");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /", answer.get(0));

    }

    @Test
    public void correctChangingWithoutArgumentsAndZeroLevelDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute("");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /", answer.get(0));

    }

    @Test
    public void correctChangingWithArgumentsOneDotsAndSlashAndTwoLevelsDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("classes");
        sessionContext.getAllowedPath().add("java");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" ../");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /classes", answer.get(0));
    }

    @Test
    public void correctChangingWithArgumentsTwoDotsAndSlashAndTwoLevelsDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("classes");
        sessionContext.getAllowedPath().add("java");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" ../../");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /", answer.get(0));
    }

    @Test
    public void correctChangingWithArgumentsThreeDotsAndSlashAndTwoLevelsDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("classes");
        sessionContext.getAllowedPath().add("java");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" ../../../");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /", answer.get(0));
    }


    @Test
    public void correctChangingWithArgumentsThreeDotsAndSlashAndThreeLevels() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("classes");
        sessionContext.getAllowedPath().add("java");
        sessionContext.getAllowedPath().add("main");
        sessionContext.getAllowedPath().add("net");

        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" ../../../");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /classes", answer.get(0));
    }

    @Test
    public void correctChangingWithArgumentsOneSlash() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" firstfolder");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /firstfolder", answer.get(0));
    }

    @Test
    public void correctChangingWithArgumentsPathAndThreeSlashes() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().push("generated");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" /thirdfolder/usecasesfolder/authusecase");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /thirdfolder/usecasesfolder/authusecase", answer.get(0));

    }


    @Test
    public void incorrectChangingWithArgumentsPathAndSlash() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" /clasdddddds");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("550 CWD failed. /clasdddddds Directory not found", answer.get(0));
    }


    @Test
    public void correctChangingWithArgumentsTwoDotsAndTwoLevelsDown() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        sessionContext.getAllowedPath().add("tmp");
        sessionContext.getAllowedPath().add("compileJava");
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" ..");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("250 CWD successful. Current directory: /tmp", answer.get(0));

    }

    @Test
    public void incorrectChangingWithArgumentsAndIncorrectPath() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" cla");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("550 CWD failed. /cla Directory not found", answer.get(0));
    }

    @Test
    public void incorrectChangingWithArgumentsAndIncorrectPathFindInDirectory() {
        String clientIp = "clientIP";
        SessionContext sessionContext = new SessionContext(clientIp);
        ChangeDirectoryUseCase changeDirectoryUseCase = new ChangeDirectoryUseCase(sessionContext);
        List<String> answer = changeDirectoryUseCase.execute(" /cla");

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("550 CWD failed. /cla Directory not found", answer.get(0));
    }


}

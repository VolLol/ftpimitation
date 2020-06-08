package UseCase;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.usecases.ListDirectoryUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ListDirectoryUseCaseTest {

    @Test
    public void correctListWithoutArgument() {

        String clientIp = "clientIp";
        String directory = null;
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(6, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("classes", answer.get(1));
        Assert.assertEquals("generated", answer.get(2));
        Assert.assertEquals("reports", answer.get(3));
        Assert.assertEquals("test-results", answer.get(4));
        Assert.assertEquals("tmp", answer.get(5));
    }


    @Test
    public void correctListWithArgument() {

        String clientIp = "clientIp";
        String directory = "/tmp";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(3, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("compileJava", answer.get(1));
        Assert.assertEquals("compileTestJava", answer.get(2));
    }

    @Test
    public void correctListWithArgumentAndShowFiles() {

        String clientIp = "clientIp";
        String directory = "/test-results/test/binary";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(3, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("output.bin", answer.get(1));
        Assert.assertEquals("output.bin.idx", answer.get(2));
    }


    @Test
    public void incorrectListWithArgumentAndEmptyFolder() {
        String clientIp = "clientIp";
        String directory = "/tmp/compileJava";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(2, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("This path is empty", answer.get(1));
    }


}
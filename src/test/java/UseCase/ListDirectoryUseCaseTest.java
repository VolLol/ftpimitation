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

        Assert.assertEquals(8, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("Mode      Name", answer.get(1));
        Assert.assertEquals("-d    classes", answer.get(2));
        Assert.assertEquals("-d    generated", answer.get(3));
        Assert.assertEquals("-d    libs", answer.get(4));
        Assert.assertEquals("-d    reports", answer.get(5));
        Assert.assertEquals("-d    test-results", answer.get(6));
        Assert.assertEquals("-d    tmp", answer.get(7));

    }


    @Test
    public void correctListWithArgument() {

        String clientIp = "clientIp";
        String directory = "/tmp";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(5, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("Mode      Name", answer.get(1));
        Assert.assertEquals("-d    compileJava", answer.get(2));
        Assert.assertEquals("-d    compileTestJava", answer.get(3));
        Assert.assertEquals("-d    jar", answer.get(4));

    }

    @Test
    public void correctListWithArgumentAndShowFiles() {

        String clientIp = "clientIp";
        String directory = "/test-results/test/binary";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(5, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("Mode      Name", answer.get(1));
        Assert.assertEquals("-f    output.bin", answer.get(2));
        Assert.assertEquals("-f    output.bin.idx", answer.get(3));
        Assert.assertEquals("-f    results.bin", answer.get(4));

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
package usecases;

import net.example.ftpimitation.utils.SessionContext;
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

        Assert.assertEquals(11, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("-----------------",answer.get(1));
        Assert.assertEquals("Type      Name", answer.get(2));
        Assert.assertEquals("-----------------",answer.get(3));
        Assert.assertEquals("dir      classes", answer.get(4));
        Assert.assertEquals("dir      generated", answer.get(5));
        Assert.assertEquals("dir      libs", answer.get(6));
        Assert.assertEquals("dir      reports", answer.get(7));
        Assert.assertEquals("dir      test-results", answer.get(8));
        Assert.assertEquals("dir      tmp", answer.get(9));
        Assert.assertEquals("-----------------", answer.get(10));


    }


    @Test
    public void correctListWithArgument() {

        String clientIp = "clientIp";
        String directory = "/tmp";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(8, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("-----------------",answer.get(1));
        Assert.assertEquals("Type      Name", answer.get(2));
        Assert.assertEquals("-----------------",answer.get(3));
        Assert.assertEquals("dir      compileJava", answer.get(4));
        Assert.assertEquals("dir      compileTestJava", answer.get(5));
        Assert.assertEquals("dir      jar", answer.get(6));
        Assert.assertEquals("-----------------",answer.get(7));


    }

    @Test
    public void correctListWithArgumentAndShowFiles() {

        String clientIp = "clientIp";
        String directory = "/libs";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(6, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("-----------------",answer.get(1));
        Assert.assertEquals("Type      Name", answer.get(2));
        Assert.assertEquals("-----------------",answer.get(3));
        Assert.assertEquals("file      ftpimitation-1.0-SNAPSHOT.jar", answer.get(4));
        Assert.assertEquals("-----------------",answer.get(5));

    }


    @Test
    public void incorrectListWithArgumentAndEmptyFolder() {
        String clientIp = "clientIp";
        String directory = "/tmp/compileJava";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(1, answer.size());
        Assert.assertEquals("This path is empty", answer.get(0));
    }


}
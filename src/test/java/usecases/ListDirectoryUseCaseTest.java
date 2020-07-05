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

        Assert.assertEquals(8, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("-----------------",answer.get(1));
        Assert.assertEquals("Type      Name", answer.get(2));
        Assert.assertEquals("-----------------",answer.get(3));
        Assert.assertEquals("dir      classes", answer.get(4));
        Assert.assertEquals("dir      libs", answer.get(5));
        Assert.assertEquals("dir      tests", answer.get(6));
        Assert.assertEquals("-----------------", answer.get(7));
    }


    @Test
    public void correctListWithArgument() {

        String clientIp = "clientIp";
        String directory = "/tests";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(7, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("-----------------",answer.get(1));
        Assert.assertEquals("Type      Name", answer.get(2));
        Assert.assertEquals("-----------------",answer.get(3));
        Assert.assertEquals("dir      parsers", answer.get(4));
        Assert.assertEquals("dir      usecases", answer.get(5));
        Assert.assertEquals("-----------------",answer.get(6));


    }

    @Test
    public void correctListWithArgumentAndShowFiles() {

        String clientIp = "clientIp";
        String directory = "/tests/usecases/authusecase";
        SessionContext sessionContext = new SessionContext(clientIp);
        ListDirectoryUseCase listDirectoryUseCase = new ListDirectoryUseCase(sessionContext);
        List<String> answer = listDirectoryUseCase.execute(directory);

        Assert.assertEquals(6, answer.size());
        Assert.assertEquals("200 Command okay.", answer.get(0));
        Assert.assertEquals("-----------------",answer.get(1));
        Assert.assertEquals("Type      Name", answer.get(2));
        Assert.assertEquals("-----------------",answer.get(3));
        Assert.assertEquals("file      authusecasetest.txt", answer.get(4));
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
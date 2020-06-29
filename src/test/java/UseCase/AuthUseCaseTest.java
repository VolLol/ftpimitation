package UseCase;

import net.example.ftpimitation.entities.UserEntity;
import net.example.ftpimitation.exception.ProblemConnectionToDatabaseException;
import net.example.ftpimitation.exception.UserNotExistException;
import net.example.ftpimitation.repository.UserRepository;
import net.example.ftpimitation.utils.SessionContext;
import net.example.ftpimitation.usecases.AuthUseCase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;


public class AuthUseCaseTest {

    private String clientIp;
    private SessionContext sessionContext;
    private AuthUseCase authUseCase;

    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientIp = "clientIp";
        sessionContext = new SessionContext(clientIp);
        authUseCase = new AuthUseCase(sessionContext, mockUserRepository);
    }

    private UserEntity createUser(String username, String cleanPassword) {
        UserEntity user = UserEntity.builder().id(1L).username(username).cleanPassword(cleanPassword).build();
        return user;
    }

    @Test
    public void correctUsernameAndPassword() throws UserNotExistException, ProblemConnectionToDatabaseException, SQLException {
        Mockito.when(mockUserRepository.findUserByUsername("user1")).thenReturn(createUser("user1", "pass1"));

        ImmutablePair<String, Boolean> result = authUseCase.execute("user1", "pass1");
        Mockito.verify(mockUserRepository).findUserByUsername("user1");

        Assert.assertEquals("Successful authentication", result.left);
        Assert.assertEquals(true, result.right);
    }

    @Test
    public void incorrectUsername() throws UserNotExistException, ProblemConnectionToDatabaseException {
        Mockito.when(mockUserRepository.findUserByUsername("user1")).thenReturn(createUser("user1", "pass1"));

        ImmutablePair<String, Boolean> result = authUseCase.execute("incorrect username", "pass");

        Mockito.verify(mockUserRepository).findUserByUsername("incorrect username");
        Assert.assertEquals("Incorrect username", result.left);
        Assert.assertEquals(false, result.right);
    }


    @Test
    public void incorrectPassword() throws UserNotExistException, ProblemConnectionToDatabaseException {
        Mockito.when(mockUserRepository.findUserByUsername("user1")).thenReturn(createUser("user1", "pass1"));

        ImmutablePair<String, Boolean> result = authUseCase.execute("user1", "incorrect password");

        Mockito.verify(mockUserRepository).findUserByUsername("user1");
        Assert.assertEquals("Incorrect password", result.left);
        Assert.assertEquals(false, result.right);
    }

}

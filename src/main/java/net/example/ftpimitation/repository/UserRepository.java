package net.example.ftpimitation.repository;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.entities.UserEntity;
import net.example.ftpimitation.exception.ProblemConnectionToDatabaseException;
import net.example.ftpimitation.exception.UserNotExistException;
import net.example.ftpimitation.utils.DBConnectionFactory;

import java.sql.*;

public class UserRepository {

    private SessionContext sessionContext;

    public UserRepository(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public UserEntity findUserByUsernameAndPassword(String username) throws UserNotExistException, ProblemConnectionToDatabaseException {
        Connection connection = null;
        UserEntity user;
        try {
            connection = DBConnectionFactory.getConnection();
            user = findUserInDatabaseByUsername(connection, username);
            return user;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new ProblemConnectionToDatabaseException("Problem close connection to database", e);
                }
            }
        }

    }


    private UserEntity findUserInDatabaseByUsername(Connection connection, String username) throws UserNotExistException {
        UserEntity user = null;
        try {
            String queryCheckUserExist = "SELECT * FROM users where username =?";
            PreparedStatement preparedStatement = connection.prepareStatement(queryCheckUserExist, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, username);
            ResultSet resultSetCheckUserExist = preparedStatement.executeQuery();
            if (resultSetCheckUserExist.next()) {
                user = UserEntity.builder()
                        .id((long) resultSetCheckUserExist.getInt(1))
                        .username(resultSetCheckUserExist.getString(2))
                        .cleanPassword(resultSetCheckUserExist.getString(3))
                        .build();
            } else {
                throw new UserNotExistException();
            }
            return user;
        } catch (SQLException e) {
            throw new UserNotExistException(e);
        }
    }
}

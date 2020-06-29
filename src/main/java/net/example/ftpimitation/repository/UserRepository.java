package net.example.ftpimitation.repository;

import net.example.ftpimitation.utils.SessionContext;
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

    public UserEntity findUserByUsername(String username) throws UserNotExistException, ProblemConnectionToDatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UserEntity user = null;
        try {
            connection = DBConnectionFactory.getConnection();
            String queryCheckUserExist = "SELECT * FROM users where username =?";
            preparedStatement = connection.prepareStatement(queryCheckUserExist, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, username);
            ResultSet resultSetCheckUserExist = preparedStatement.executeQuery();
            if (resultSetCheckUserExist.next()) {
                user = UserEntity.builder()
                        .id((long) resultSetCheckUserExist.getInt(1))
                        .username(resultSetCheckUserExist.getString(2))
                        .cleanPassword(resultSetCheckUserExist.getString(3))
                        .build();
            }
            return user;
        } catch (SQLException e) {
            throw new UserNotExistException(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new ProblemConnectionToDatabaseException("Problem with preparedStatement to database ", e);
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new ProblemConnectionToDatabaseException("Problem with close connection to database", e);
                }
            }
        }
    }
}

package net.example.ftpimitation.repository;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.ProblemConnectionToDatabaseException;
import net.example.ftpimitation.exception.UserNotExistException;
import net.example.ftpimitation.utils.DBConnectionFactory;

import java.sql.*;

public class UserRepository {

    private SessionContext sessionContext;

    public UserRepository(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public void checkUsernameAndPassword(String username, String cleanPassword) throws UserNotExistException, IncorrectPasswordException, ProblemConnectionToDatabaseException {
        Connection connection = null;

        try {
            connection = DBConnectionFactory.getConnection();
            checkingUserExist(connection, username);
            checkingCorrectPassword(connection, username, cleanPassword);
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

    private void checkingUserExist(Connection connection, String username) throws UserNotExistException {
        try {
            String queryCheckUserExist = "SELECT * FROM users where username =?";

            PreparedStatement preparedStatement = connection.prepareStatement(queryCheckUserExist, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, username);
            ResultSet resultSetCheckUserExist = preparedStatement.executeQuery();
            resultSetCheckUserExist.last();
            int countRows = resultSetCheckUserExist.getRow();
            resultSetCheckUserExist.beforeFirst();
            if (countRows > 0) {
                while (resultSetCheckUserExist.next()) {
                    System.out.println("[" + sessionContext.getClientIp() + "] User exist. Сontinue checking");
                }
            } else {
                throw new UserNotExistException();
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new UserNotExistException(e);
        }
    }

    private void checkingCorrectPassword(Connection connection, String username, String cleanPassword) throws IncorrectPasswordException {
        try {
            String queryPasswordCorrect = "SELECT * FROM users where username =? and clean_password =?";
            PreparedStatement preparedStatement = connection.prepareStatement(queryPasswordCorrect);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, cleanPassword);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("[" + sessionContext.getClientIp() + "] Password correct");
            } else {
                throw new IncorrectPasswordException();
            }
        } catch (SQLException e) {
            throw new IncorrectPasswordException(e);
        }
    }


}

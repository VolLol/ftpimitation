package net.example.ftpimitation.repository;

import net.example.ftpimitation.SessionContext;
import net.example.ftpimitation.exception.IncorrectPasswordException;
import net.example.ftpimitation.exception.UserNotExistException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.sql.*;

public class UserRepository {

    private static final String DB_NAME = "postgres";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/" + DB_NAME;
    private static final String USER = "postgres";
    private static final String PASS = "password";
    private SessionContext sessionContext;

    public UserRepository(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public void checkUsernameAndPassword(String username, String cleanPassword) throws UserNotExistException, IncorrectPasswordException {
        disableWarning();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] PostgreSQL JDBC Driver is not found.");
        }
        System.out.println("[" + sessionContext.getClientIp() + "] PostgreSQL JDBC Driver successfully connected");
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("[" + sessionContext.getClientIp() + "] Start checking user and password");
            checkingUserExist(connection, username);
            checkingCorrectPassword(connection, username, cleanPassword);
            System.out.println("[" + sessionContext.getClientIp() + "] Finish checking user and password");
        } catch (SQLException e) {
            System.out.println("[" + sessionContext.getClientIp() + "] Connection Failed");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void checkingUserExist(Connection connection, String username) throws UserNotExistException, SQLException {
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
    }

    private void checkingCorrectPassword(Connection connection, String username, String cleanPassword) throws IncorrectPasswordException, SQLException {
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
    }

    private void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);
            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
        }
    }


}

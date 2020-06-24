package net.example.ftpimitation.utils;

import net.example.ftpimitation.exception.ProblemConnectionToDatabaseException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {
    private static final String DB_NAME = "postgres";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/" + DB_NAME;
    private static final String USER = "postgres";
    private static final String PASS = "password";
    private static final Object sinc = new Object();

    public static Connection getConnection() throws ProblemConnectionToDatabaseException {
        synchronized (sinc) {
            try {
                disableWarning();
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                return connection;
            } catch (ClassNotFoundException e) {
                throw new ProblemConnectionToDatabaseException("Database driver not found", e);
            } catch (SQLException e) {
                throw new ProblemConnectionToDatabaseException("Can't connect to database", e);
            }
        }
    }

    private static void disableWarning() {
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

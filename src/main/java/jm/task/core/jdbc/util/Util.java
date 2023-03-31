package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util  {
    private static volatile Util instance;
    public static Util getInstance() {
        Util localUtil = instance;
        if (localUtil == null) {
            synchronized (Util.class) {
                localUtil = instance;
                if (localUtil == null) {
                    instance = new Util();
                }
            }
        }
        return localUtil;
    }

    public static Connection getConnection()  {
        String URL = "jdbc:mysql://localhost:3306/database";
        String login = "ry";
        String password = "1234";
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try  {
            connection = DriverManager.getConnection(URL, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

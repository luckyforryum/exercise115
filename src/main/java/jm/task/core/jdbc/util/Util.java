package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

    public static Session getSessionFactory()  {
        String URL = "jdbc:mysql://localhost:3306/database";
        String login = "ry";
        String password = "1234";
        SessionFactory sessionFactory = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try  {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", URL);
            properties.setProperty("hibernate.connection.username", login);
            properties.setProperty("hibernate.connection.password", password);
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sessionFactory.openSession();
    }
}

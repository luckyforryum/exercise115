package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.Resultset;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl()  {

    }
    private Util util = new Util();




    public void createUsersTable() {
        try (Connection connection = util.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT)");

        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.getInstance().getConnection();Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS test ");

        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO test (name, lastName, age) VALUES (? ,?, ?);";
        Connection connection = null;
        try {
            connection = util.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM test WHERE id = ?";
        try (Connection connection = util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        String sql = "SELECT  * FROM test";
        try (Connection connection = util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs =  preparedStatement.executeQuery();
            while(rs.next()) {
                long id  = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");
                User newUser = new User(name, lastName, age);
                newUser.setId(id);
                user.add(newUser);

            }
        } catch (SQLException e) {

        }
        return user;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM test";
        try (Connection connection = util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }
}

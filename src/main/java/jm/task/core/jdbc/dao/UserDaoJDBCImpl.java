package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.Resultset;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl()  {

    }
    private Util util = new Util();

    private Connection connection1 = util.getInstance().getConnection();
    private Connection connection2 = util.getInstance().getConnection();
    private Connection connection3 = util.getInstance().getConnection();
    private Connection connection4 = util.getInstance().getConnection();
    private Connection connection5 = util.getInstance().getConnection();
    private Connection connection6 = util.getInstance().getConnection();
    public void createUsersTable() {
        try (Statement statement = connection1.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT)");
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection2.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS test ");
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO test (name, lastName, age) VALUES (? ,?, ?);";

        try (PreparedStatement preparedStatement = connection3.prepareStatement(sql)) {

            connection3.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            connection3.commit();
        } catch (SQLException e) {

        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM test WHERE id = ?";
        try (PreparedStatement preparedStatement = connection4.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        String sql = "SELECT  * FROM test";
        try (PreparedStatement preparedStatement = connection5.prepareStatement(sql)) {

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
        try (PreparedStatement preparedStatement = connection6.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}

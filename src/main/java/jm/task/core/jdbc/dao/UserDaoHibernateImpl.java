package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private Util util = new Util();

    @Override
    public void createUsersTable() {
        try(Session session = util.getInstance().getSessionFactory()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS test (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age INT)").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

        }

    }

    @Override
    public void dropUsersTable() {
        try(Session session = util.getInstance().getSessionFactory()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS test").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.getInstance().getSessionFactory();
        try {
            session.getTransaction().begin();
            session.save(new User(name,lastName,age));
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = util.getInstance().getSessionFactory()) {
            session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try(Session session = util.getInstance().getSessionFactory()) {
            session.beginTransaction();
            user = session.createCriteria(User.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM User";
        try(Session session = util.getInstance().getSessionFactory()) {
            session.beginTransaction();
            session.createQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }
}

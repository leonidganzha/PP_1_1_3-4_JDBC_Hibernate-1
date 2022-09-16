package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    public void createUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createNativeQuery ("""
                CREATE TABLE IF NOT EXISTS users
                (id INTEGER not NULL AUTO_INCREMENT,
                FirstName VARCHAR(255),
                LastName VARCHAR(255),
                Age INTEGER,
                PRIMARY KEY (id))
                """).executeUpdate();
        session.getTransaction().commit();
    }

    public void dropUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createNativeQuery ("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();
        session.beginTransaction();
        session.persist(new User(name, lastName, age));
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createQuery(" delete from User where id=id").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession();
        session.beginTransaction();
        List users = session.createQuery("from User").list();
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        session.createQuery(" delete from User").executeUpdate();
        session.getTransaction().commit();
    }
}

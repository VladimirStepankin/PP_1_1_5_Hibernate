package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private static Transaction transaction = null;

    @Override
    public void createUsersTable() {
        try {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ("
                                       + "id BIGINT PRIMARY KEY AUTO_INCREMENT, "
                                       + "name VARCHAR(50), "
                                       + "lastName VARCHAR(50), "
                                       + "age TINYINT "
                                       + ")").executeUpdate();
                transaction.commit();
            }
        } catch (HibernateException e) {
            System.out.println("An error occurred while creating the table" + e.getMessage());
        }
        System.out.println("DB created successful by Hibernate");

    }

    @Override
    public void dropUsersTable() {
        try {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                transaction.commit();
            }
        } catch (HibernateException e) {
            System.out.println("An error occurred while deleting the table" + e.getMessage());
        }
        System.out.println("The table was deleted successfully");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
            }
        } catch (HibernateException e) {
            System.out.println("An error occurred while saving the user" + e.getMessage());
        }
        System.out.println("User c именем - " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(session.get(User.class, id));
                transaction.commit();
            }
        } catch (HibernateException e) {
            System.out.println("An error occurred while deleting  the user" + e.getMessage());
        }
        System.out.println("User c id - " + id + " удален из базы данных");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                userList = session.createQuery("from User").getResultList();
                transaction.commit();
            }
        } catch (HibernateException e) {
            System.out.println("An error occurred while retrieving the list of users" + e.getMessage());
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
                transaction.commit();
            }
        } catch (HibernateException e) {
            System.out.println("An error occurred while clearing the table" + e.getMessage());
        }
        System.out.println("The table was cleared successfully");
    }
}

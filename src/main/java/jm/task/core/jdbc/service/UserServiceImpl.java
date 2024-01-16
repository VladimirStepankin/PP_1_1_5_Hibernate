package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao daoJDBC = new UserDaoJDBCImpl();
    private final UserDao daoHibernate = new UserDaoHibernateImpl();

    public UserServiceImpl() {
    }

    @Override
    public void createUsersTable() {
        daoHibernate.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        daoHibernate.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        daoHibernate.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        daoHibernate.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return daoHibernate.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        daoHibernate.cleanUsersTable();
    }
}

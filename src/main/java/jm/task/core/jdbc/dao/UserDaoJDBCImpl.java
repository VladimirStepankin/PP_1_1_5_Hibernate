package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    final private Connection connection = Util.getConnection();

    public void createUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  name VARCHAR(50)," +
                        "  lastName VARCHAR(50)," +
                        "  age TINYINT," +
                        "  PRIMARY KEY (id));");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while creating the table" + e.getMessage());
        }
        System.out.println("DB created successful by JDBC");
    }

    public void dropUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS users_db.users");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting the table" + e.getMessage());
        }
        System.out.println("The table was deleted successfully");
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users_db.users" +
                    " (name, lastName, age) VALUES (?, ?, ?)")) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while saving the user" + e.getMessage());
        }
        System.out.println("User c именем - " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users_db.users WHERE id = ?")) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting  the user" + e.getMessage());
        }
        System.out.println("User c id - " + id + " удален из базы данных");
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users_db.users");
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving the list of users" + e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE users_db.users");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while clearing the table" + e.getMessage());
        }
        System.out.println("The table was cleared successfully");
    }
}

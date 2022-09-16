package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public void createUsersTable() {
        try (Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("""
                CREATE TABLE IF NOT EXISTS users
                (id INTEGER not NULL AUTO_INCREMENT,
                FirstName VARCHAR(255),
                LastName VARCHAR(255),
                Age INTEGER,
                PRIMARY KEY (id))
                """);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS users");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users(FirstName, LastName, Age) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setLong(3, age);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            stmt.setInt(1, (int) id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("FirstName"),
                                     rs.getString("LastName"),
                                     rs.getByte("Age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("TRUNCATE TABLE users");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
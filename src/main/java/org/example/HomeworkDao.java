package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeworkDao {
    private Connection connection;

    public HomeworkDao() {
        connection = DataBaseConnection.getConnection();
    }

    public void addHomework(Homework homework) {
        String sql = "INSERT INTO Homework (name, description) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, homework.getName());
            statement.setString(2, homework.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding homework to the database", e);
        }
    }

    public void updateHomework(Homework homework) {
        String sql = "UPDATE Homework SET name = ?, description = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, homework.getName());
            statement.setString(2, homework.getDescription());
            statement.setInt(3, homework.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating homework in the database", e);
        }
    }

    public void deleteHomework(int id) {
        String sql = "DELETE FROM Homework WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting homework from the database", e);
        }
    }

    public List<Homework> getAllHomework() {
        String sql = "SELECT * FROM Homework";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Homework> homeworkList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                Homework homework = new Homework(name, description);
                homeworkList.add(homework);
            }
            return homeworkList;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all homework from the database", e);
        }
    }

    public Homework getHomeworkById(int id) {
        String sql = "SELECT * FROM Homework WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Homework homework = new Homework(name, description);
                    return homework;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting homework by ID from the database", e);
        }
    }

    public List<Homework> getHomeworkByName(String name) {
        String sql = "SELECT * FROM Homework WHERE name LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Homework> homeworkList = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String homeworkName = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    Homework homework = new Homework(homeworkName, description);
                    homeworkList.add(homework);
                }
                return homeworkList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting homework by name from the database", e);
        }
    }
}

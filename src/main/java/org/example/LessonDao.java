package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
    private Connection connection;

    public LessonDao() {
        connection = DataBaseConnection.getConnection();
    }

    public void addLesson(Lesson lesson) {
        String sql = "INSERT INTO Lesson (name, homework_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, lesson.getName());
            statement.setInt(2, lesson.getHomework().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding lesson to the database", e);
        }
    }

    public void deleteLesson(int id) {
        String sql = "DELETE FROM Lesson WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting lesson from the database", e);
        }
    }

    public List<Lesson> getAllLessons() {
        String sql = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Lesson> lessons = new ArrayList<>();
            while (resultSet.next()) {
                int lessonId = resultSet.getInt(1);
                String lessonName = resultSet.getString(2);
                int homeworkId = resultSet.getInt(3);
                String homeworkName = resultSet.getString(4);
                String homeworkDescription = resultSet.getString(5);
                Homework homework = new Homework(homeworkName, homeworkDescription);
                Lesson lesson = new Lesson(lessonId, lessonName, homework);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all lessons from the database", e);
        }
    }

    public Lesson getLessonById(int id) {
        String sql = "SELECT Lesson.id, Lesson.name, Homework.id, Homework.name, Homework.description " +
                "FROM Lesson " +
                "JOIN Homework ON Lesson.homework_id = Homework.id " +
                "WHERE Lesson.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int lessonId = resultSet.getInt(1);
                    String lessonName = resultSet.getString(2);
                    int homeworkId = resultSet.getInt(3);
                    String homeworkName = resultSet.getString(4);
                    String homeworkDescription = resultSet.getString(5);
                    Homework homework = new Homework(homeworkName, homeworkDescription);
                    Lesson lesson = new Lesson(lessonId, lessonName, homework);
                    return lesson;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting lesson by ID from the database", e);
        }
    }
}

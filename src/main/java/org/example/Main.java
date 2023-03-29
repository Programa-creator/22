package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Homework homework1 = new Homework("Домашня робота з математики", "Виконати завдання №1, №2 та №3 на сторінці 23");
        HomeworkDao homeworkDao = new HomeworkDao();
        homeworkDao.addHomework(homework1);

        Lesson lesson1 = new Lesson("Математика", homework1.getId());
        LessonDao lessonDao = new LessonDao();
        lessonDao.addLesson(lesson1);

        homework1.setDescription("Виконати завдання №1, №2, №3 та №4 на сторінці 23");
        homeworkDao.updateHomework(homework1);

        List<Lesson> lessons = lessonDao.getAllLessons();
        for (Lesson lesson : lessons) {
            Homework homework = homeworkDao.getHomeworkById(lesson.getHomeworkId());
            System.out.println("Урок: " + lesson.getName());
            System.out.println("Домашня робота: " + homework.getName());
            System.out.println("Опис домашньої роботи: " + homework.getDescription());
            System.out.println();
        }

        List<Homework> homeworkList = homeworkDao.getHomeworkByName("математика");
        for (Homework homework : homeworkList) {
            System.out.println("Назва домашньої роботи: " + homework.getName());
            System.out.println("Опис домашньої роботи: " + homework.getDescription());
            System.out.println();
        }

        lessonDao.deleteLesson(lesson1.getId());
        homeworkDao.deleteHomework(homework1.getId());
    }
}


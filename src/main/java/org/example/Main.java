package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Створення об'єкту Homework та збереження у базі даних
        Homework homework1 = new Homework("Домашня робота з математики", "Виконати завдання №1, №2 та №3 на сторінці 23");
        HomeworkDao homeworkDao = new HomeworkDao();
        homeworkDao.addHomework(homework1);

        // Створення об'єкту Lesson та збереження у базі даних
        Lesson lesson1 = new Lesson("Математика", homework1.getId());
        LessonDao lessonDao = new LessonDao();
        lessonDao.addLesson(lesson1);

        // Оновлення інформації про домашню роботу
        homework1.setDescription("Виконати завдання №1, №2, №3 та №4 на сторінці 23");
        homeworkDao.updateHomework(homework1);

        // Отримання всіх уроків та пов'язаних з ними домашніх робіт
        List<Lesson> lessons = lessonDao.getAllLessons();
        for (Lesson lesson : lessons) {
            Homework homework = homeworkDao.getHomeworkById(lesson.getHomeworkId());
            System.out.println("Урок: " + lesson.getName());
            System.out.println("Домашня робота: " + homework.getName());
            System.out.println("Опис домашньої роботи: " + homework.getDescription());
            System.out.println();
        }

        // Пошук домашньої роботи за назвою
        List<Homework> homeworkList = homeworkDao.getHomeworkByName("математика");
        for (Homework homework : homeworkList) {
            System.out.println("Назва домашньої роботи: " + homework.getName());
            System.out.println("Опис домашньої роботи: " + homework.getDescription());
            System.out.println();
        }

        // Видалення уроку та пов'язаної з ним домашньої роботи
        lessonDao.deleteLesson(lesson1.getId());
        homeworkDao.deleteHomework(homework1.getId());
    }
}


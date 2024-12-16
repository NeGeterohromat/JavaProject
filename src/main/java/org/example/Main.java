package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import org.example.course.Course;
import org.example.course.Module;
import org.example.course.ModuleScores;
import org.example.course.Student;
import org.example.db.DBRepository;
import org.example.visual.MenuFrame;
import org.example.vk.VKRepository;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final HashSet<Integer> groupIDs = new HashSet<>();

    public static void main(String[] args) throws IOException, ClientException, ApiException {
        groupIDs.add(22941070); //главная УрФУ
        groupIDs.add(6214974); //главная Радиофак
        groupIDs.add(195681601); //главная ФИИТ
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
//        Course javaCourse = CourseCSVParser.getParsedCourse("java", Paths.get("java-rtf.csv"));
//        VKRepository vk = new VKRepository();
//        vk.fillCourse(javaCourse, groupIDs, "УрФУ им. первого Президента России Б. Н. Ельцина");
//
//        DBRepository.connect();
//        DBRepository.createCourseTable();
//        DBRepository.createStudentsTable();
//        DBRepository.createModuleScoresTable();
//        DBRepository.saveModules(javaCourse.getModules());
//        DBRepository.saveStudents(javaCourse.getStudentIDs().stream().map(javaCourse::getStudent).toList());
//        DBRepository.saveModuleScores(javaCourse);

        Course courseFromDB = DBRepository.getCourse("DBJava");

        new MenuFrame(courseFromDB);
    }

    public static void printSt(Student s, Course c) {
        for (Module m : c.getModules()) {
            ModuleScores ms = m.getScores(s);
            System.out.printf("Модуль %s КВ: %d; УПР: %d; ДЗ: %d\n", m.getName(), ms.getQuestionsScore(), ms.getExercisesScore(), ms.getPracticeScore());
        }
    }
}

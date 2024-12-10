package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
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
//
//
//        printVK(javaCourse, vk);
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
        //new ScatterPlotDrawer(courseFromDB, courseFromDB.getModule("10. Функциональные интерфейсы. Stream API"), ScatterPlotDrawer.PlotType.Questions).setVisible(true);
    }

    public static void printSt(Student s, Course c) {
        for (Module m : c.getModules()) {
            ModuleScores ms = m.getScores(s);
            System.out.printf("Модуль %s КВ: %d; УПР: %d; ДЗ: %d\n", m.getName(), ms.getQuestionsScore(), ms.getExercisesScore(), ms.getPracticeScore());
        }
    }

    public static void printVK(Course c, VKRepository vk) throws ClientException, ApiException {
        for (String id : c.getStudentIDs()) {
            List<UserFull> list = vk.getUsersFieldsByName(c.getStudent(id).getFullName(), Fields.STATUS, Fields.UNIVERSITIES, Fields.CITY);
            UserFull user = vk.getUserByUniversityNameAndGroupIDs(list, "УрФУ им. первого Президента России Б. Н. Ельцина",groupIDs);
            if (user != null) {
                c.getStudent(id).setStatus(user.getStatus());
                c.getStudent(id).setCity(user.getCity() == null ? null : user.getCity().getTitle());
                c.getStudent(id).setVkID(user.getId());
                System.out.printf("vkID: %d; Name: %s %s; Status: %s; City: %s\n", user.getId(), user.getFirstName(), user.getLastName(), c.getStudent(id).getStatus(), c.getStudent(id).getCity());
            } else {
                System.out.println("UserNotFound");
            }

            try{
                TimeUnit.MILLISECONDS.sleep(250);
            } catch ( InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

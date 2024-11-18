package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClientException, ApiException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Course javaCource = CourseCSVParser.getParsedCourse("java", Paths.get("java-rtf.csv"));
        VKRepository vk = new VKRepository();

        printVK(javaCource, vk);
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
            UserFull user = vk.getUserByUniversityName(list, "УрФУ им. первого Президента России Б. Н. Ельцина");
            if (user != null) {
                c.getStudent(id).setStatus(user.getStatus());
                c.getStudent(id).setCity(user.getCity() == null ? null : user.getCity().getTitle());
                System.out.printf("vkID: %d; Name: %s %s; Status: %s; City: %s\n", user.getId(), user.getFirstName(), user.getLastName(), c.getStudent(id).getStatus(), c.getStudent(id).getCity());
            } else {
                System.out.println("UserNotFound");
            }
        }
    }
}
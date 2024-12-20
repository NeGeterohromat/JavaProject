package org.example.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.course.Course;
import org.example.course.Module;
import org.example.course.ModuleScores;
import org.example.course.Student;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;

public class CourseCSVParser {

    public static Course getParsedCourse(String name, Path path) throws IOException {
        Course cource = new Course(name);
        try (CSVParser doc = CSVParser.parse(path, Charset.forName("windows-1251"), CSVFormat.newFormat(';'))) {
            ArrayList<Integer> moduleStartIndexes = null;
            ArrayList<int[]> moduleScoresIndexes = null;
            CSVRecord first = null;
            for (CSVRecord record : doc) {
                if (record.getRecordNumber() > 3) {
                    parseModule(cource,record,moduleStartIndexes,moduleScoresIndexes,first);
                } else if (record.getRecordNumber() == 1) {
                    moduleStartIndexes = getModuleStartIndexes(record, cource);
                    first = record;
                } else if (record.getRecordNumber() == 2) {
                    moduleScoresIndexes = getModuleScoresIndexes(record, moduleStartIndexes);
                }
            }
        }
        return cource;
    }

    public static ArrayList<Integer> getModuleStartIndexes(CSVRecord record, Course course) {
        ArrayList<Integer> moduleStartIndexes = new ArrayList<>();
        for (int i = 0; i < record.size(); i++) {
            if (record.get(i).matches("\\d+\\. .*")) {
                moduleStartIndexes.add(i);
                course.addModule(new org.example.course.Module(record.get(i)));
            }
        }
        return moduleStartIndexes;
    }

    public static ArrayList<int[]> getModuleScoresIndexes(CSVRecord record, ArrayList<Integer> moduleStartIndexes) {
        ArrayList<int[]> moduleScoresIndexes = new ArrayList<>();
        for (int i = 0; i < moduleStartIndexes.size(); i++) {
            moduleScoresIndexes.add(getEmpty());
            for (int j = moduleStartIndexes.get(i); j < (i==moduleStartIndexes.size()-1?moduleStartIndexes.get(i)+1: moduleStartIndexes.get(i + 1)); j++) {
                switch (record.get(j)) {
                    case "КВ" -> moduleScoresIndexes.get(i)[0] = j;
                    case "УПР" -> moduleScoresIndexes.get(i)[1] = j;
                    case "ДЗ" -> moduleScoresIndexes.get(i)[2] = j;
                }
            }
        }
        return moduleScoresIndexes;
    }

    public static int[] getEmpty(){
        int[] arr = new int[3];
        arr[0] = -1;
        arr[1] = -1;
        arr[2] = -1;
        return arr;
    }

    public static void parseModule(Course course, CSVRecord record,ArrayList<Integer> moduleStartIndexes,ArrayList<int[]> moduleScoresIndexes, CSVRecord first) {
        Student student = new Student(record.get(1),record.get(0));
        for (int j = 0; j < moduleStartIndexes.size(); j++) {
            int i = j;
            Module module = course.getModules().stream().filter(m -> m.getName().equals(first.get(moduleStartIndexes.get(i)))).findAny().get();
            ModuleScores ms = new ModuleScores(moduleScoresIndexes.get(j)[0] == -1 ? 0 : Integer.parseInt(record.get(moduleScoresIndexes.get(j)[0])),
                    moduleScoresIndexes.get(j)[1] == -1 ? 0 : Integer.parseInt(record.get(moduleScoresIndexes.get(j)[1])),
                    moduleScoresIndexes.get(j)[2] == -1 ? 0 : Integer.parseInt(record.get(moduleScoresIndexes.get(j)[2])));
            module.addStudent(student, ms);
        }
        course.addStudent(student);
    }
}

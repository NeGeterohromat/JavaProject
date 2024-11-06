import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Course javaCource = CourseCSVParser.getParsedCourse("java",Paths.get("java-rtf.csv"));

        printSt(new Student("481e3c94-365d-40d0-a3fe-6797b45578cd"),javaCource);
    }

    public static void printSt(Student s, Course c){
        for (Module m: c.getModules()){
            ModuleScores ms = m.getScores(s);
            System.out.printf("Модуль %s КВ: %d; УПР: %d; ДЗ: %d\n",m.getName(),ms.getQuestionsScore(),ms.getExercisesScore(),ms.getPracticeScore());
        }
    }
}
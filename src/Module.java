import java.util.HashMap;

public class Module {
    private final String name;

    private HashMap<Student, ModuleScores> studentsScores;

    public Module(String name) {
        this.name = name;
    }

    public void addStudent(Student student, ModuleScores scores) {
        studentsScores.put(student, scores);
    }

    public ModuleScores getScores(Student student) {
        return studentsScores.getOrDefault(student, null);
    }

    public String getName(){
        return name;
    }
}

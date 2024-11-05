import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Course javaCource = new Course("java");
        CSVParser doc = CSVParser.parse(Paths.get("java-rtf.csv"), Charset.forName("windows-1251"), CSVFormat.newFormat(';'));
        List<CSVRecord> recs = doc.getRecords();
        ArrayList<Integer> moduleStartIndexes = new ArrayList<>();
        ArrayList<int[]> moduleScoresIndexes = new ArrayList<>();
        for (int i = 0; i< recs.getFirst().size(); i++){
            if (recs.getFirst().get(i).matches("\\d+\\. .*")){
                moduleStartIndexes.add(i);
                int[] indexes = new int[3];
                indexes[0] = -1;
                indexes[1] = -1;
                indexes[2] = -1;
                moduleScoresIndexes.add(indexes);
                javaCource.addModule(new Module(recs.getFirst().get(i)));
            }
        }
        for (int i = 0; i<moduleStartIndexes.size()-1;i++){
            for (int j=moduleStartIndexes.get(i);j<moduleStartIndexes.get(i+1);j++){
                switch (recs.get(1).get(j)){
                    case "КВ" -> moduleScoresIndexes.get(i)[0]=j;
                    case "УПР" -> moduleScoresIndexes.get(i)[1]=j;
                    case "ДЗ" -> moduleScoresIndexes.get(i)[2]=j;
                }
            }
        }
        for (int i = 2; i< recs.size();i++){
            Student student = new Student(recs.get(i).get(1));
            for (int j = 0;j<moduleStartIndexes.size();j++){
                Module module = javaCource.getModule(recs.getFirst().get(moduleStartIndexes.get(j)));
                ModuleScores ms = new ModuleScores(moduleScoresIndexes.get(j)[0]==-1?0: Integer.parseInt(recs.get(i).get(moduleScoresIndexes.get(j)[0])),
                        moduleScoresIndexes.get(j)[1]==-1?0: Integer.parseInt(recs.get(i).get(moduleScoresIndexes.get(j)[1])),
                        moduleScoresIndexes.get(j)[2]==-1?0: Integer.parseInt(recs.get(i).get(moduleScoresIndexes.get(j)[2])));
                module.addStudent(student,ms);
            }
        }

        printSt(new Student("481e3c94-365d-40d0-a3fe-6797b45578cd"),javaCource);
    }

    public static void printSt(Student s, Course c){
        for (Module m: c.getModules()){
            ModuleScores ms = m.getScores(s);
            System.out.printf("Модуль %s КВ: %d; УПР: %d; ДЗ: %d\n",m.getName(),ms.getQuestionsScore(),ms.getExercisesScore(),ms.getPracticeScore());
        }
    }
}
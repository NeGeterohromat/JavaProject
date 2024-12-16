package org.example.visual;

import org.example.course.Module;
import org.example.course.Student;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.Random;

public class ChartDataMapper {
    private static Random r = new Random();

    public static XYSeriesCollection createQuestionToPracticeDataset(Module module, List<Student> students){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries seriesQ = new XYSeries("Questions to practice");
        XYSeries seriesE = new XYSeries("Exercises to practice");
        int maxExScore = students.stream().mapToInt(s->module.getScores(s).getExercisesScore()).max().getAsInt();
        int maxQuScore = students.stream().mapToInt(s->module.getScores(s).getQuestionsScore()).max().getAsInt();
        int maxPrScore = students.stream().mapToInt(s->module.getScores(s).getPracticeScore()).max().getAsInt();
        students.forEach(s-> {
            seriesQ.add(getRandomShift(getPercent(module.getScores(s).getQuestionsScore(),maxQuScore))
                    ,getRandomShift(getPercent(module.getScores(s).getPracticeScore(),maxPrScore)));
            seriesE.add(getRandomShift(getPercent(module.getScores(s).getExercisesScore(),maxExScore))
                    ,getRandomShift(getPercent(module.getScores(s).getPracticeScore(),maxPrScore)));
        });

        dataset.addSeries(seriesQ);
        dataset.addSeries(seriesE);
        return dataset;
    }

    private static double getPercent(int number, int max){
        if (max==0){
            return 0;
        }
        return (double) (number * 100) /max;
    }

    private static double getRandomShift(double number){
        return number+r.nextDouble(-5,5);
    }
}

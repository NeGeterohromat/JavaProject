package org.example;

import org.jfree.data.xy.DefaultXYDataset;
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

        students.forEach(s-> {
            seriesQ.add(getRandomShift(module.getScores(s).getQuestionsScore()),getRandomShift(module.getScores(s).getPracticeScore()));
            seriesE.add(getRandomShift(module.getScores(s).getExercisesScore()),getRandomShift(module.getScores(s).getPracticeScore()));
        });

        dataset.addSeries(seriesQ);
        dataset.addSeries(seriesE);
        return dataset;
    }

    private static double getRandomShift(int number){
        return number+r.nextDouble(-0.2,0.2);
    }
}

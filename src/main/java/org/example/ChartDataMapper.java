package org.example;

import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.function.Function;

public class ChartDataMapper {
    public static XYSeriesCollection createQuestionToPracticeDataset(Module module, List<Student> students, ScatterPlotDrawer.PlotType type){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(String.format("%s to practice",type.name()));

        Function<Student,Integer> func = s -> switch (type){
            case Questions -> module.getScores(s).getQuestionsScore();
            case Exercises -> module.getScores(s).getExercisesScore();
        };

        students.forEach(s-> series.add(func.apply(s).intValue(),module.getScores(s).getPracticeScore()));

        dataset.addSeries(series);
        return dataset;
    }
}

package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.RectangleInsets;

import javax.swing.*;
import java.util.List;

public class ScatterPlotDrawer extends JFrame {
    public ScatterPlotDrawer(Course course, Module module,PlotType type){
        super(module.getName());
        List<Student> l = course.getStudentIDs().stream().map(course::getStudent).toList();

        setContentPane(createModulePanel(module,
                course.getStudentIDs().stream().map(course::getStudent).toList(),
                type));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600,300);
    }

    public static JPanel createModulePanel(Module module, List<Student> students, PlotType type){
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                module.getName(),
                type.name(),
                "Practice",
                ChartDataMapper.createQuestionToPracticeDataset(module,students,type)
        );
        scatterPlot.setPadding(new RectangleInsets(4,8,2,2));
        return new ChartPanel(scatterPlot);
    }

    enum PlotType{
        Questions,
        Exercises
    }
}

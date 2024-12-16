package org.example.visual;

import org.example.course.Course;
import org.example.course.Module;
import org.example.course.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.RectangleInsets;

import javax.swing.*;
import java.util.List;

public class ScatterPlotDrawer extends JFrame {
    public ScatterPlotDrawer(Course course, org.example.course.Module module){
        super(module.getName());

        setContentPane(createModulePanel(module,
                course.getStudentIDs().stream().map(course::getStudent).toList()));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600,300);
    }

    public static JPanel createModulePanel(Module module, List<Student> students){
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                module.getName(),
                "Tasks %",
                "Practice %",
                ChartDataMapper.createQuestionToPracticeDataset(module,students)
        );
        scatterPlot.setPadding(new RectangleInsets(4,8,2,2));
        return new ChartPanel(scatterPlot);
    }
}

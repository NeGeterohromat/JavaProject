package org.example.visual;

import org.example.course.Course;
import org.example.course.Module;

import javax.swing.*;

public class MenuFrame extends JFrame {

    public MenuFrame(Course course) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

        for (int i = 0; i < 12; i++) {
            JButton b = new JButton();
            b.setBounds(10, 10 + 30 * i, 470, 25);
            Module m = course.getModules().stream().toList().get(i);
            b.addActionListener(e->callScatterPlotDrawer(course,m.getName()));
            b.setText(m.getName());
            add(b);
        }
    }

    private void callScatterPlotDrawer(Course course, String  moduleName){
        new ScatterPlotDrawer(course, course.getModule(moduleName)).setVisible(true);
    }
}

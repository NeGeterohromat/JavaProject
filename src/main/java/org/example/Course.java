package org.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Course {
    private final String name;
    private HashMap<String, Module> modules = new HashMap<>();
    private HashMap<String, Student> students = new HashMap<>();

    public Course(String name) {
        this.name = name;
    }

    public void addModule(Module m) {
        modules.put(m.getName(), m);
    }

    public void addStudent(Student s) {
        students.put(s.getUlearnID(), s);
    }

    public Module getModule(String name) {
        return modules.getOrDefault(name, null);
    }

    public Student getStudent(String ulearnID) {
        return students.getOrDefault(ulearnID, null);
    }

    public Set<String> getStudentIDs() {
        return students.keySet();
    }

    public Collection<Module> getModules() {
        return modules.values();
    }
}

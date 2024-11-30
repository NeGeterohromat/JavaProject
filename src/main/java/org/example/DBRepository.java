package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBRepository {
    private static Connection conn = null;
    private static final String URL = "jdbc:sqlite:C:/Users/user/Documents/GitHub/JavaProject/testDB.db";

    public static void connect(){
        try{
            conn = DriverManager.getConnection(URL);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void createCourseTable(){
        String sql = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS course (\n")
                .append(" id integer PRIMARY KEY,\n")
                .append(" moduleName text NOT NULL\n")
                .append(");")
                .toString();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createStudentsTable(){
        String sql = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS students (\n")
                .append(" ulearnID text PRIMARY KEY,\n")
                .append(" fullName text NOT NULL,\n")
                .append(" vkID integer,\n")
                .append(" status text,\n")
                .append(" city text\n")
                .append(");")
                .toString();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void createModuleScoresTable(){
        String sql = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS moduleScores (\n")
                .append(" id integer PRIMARY KEY,\n")
                .append(" moduleID integer NOT NULL,\n")
                .append(" studentUlearnID integer NOT NULL,\n")
                .append(" questionsScore integer NOT NULL,\n")
                .append(" exercisesScore integer NOT NULL,\n")
                .append(" practiceScore integer NOT NULL,\n")
                .append(" FOREIGN KEY (moduleID)  REFERENCES course (id),")
                .append(" FOREIGN KEY (studentUlearnID)  REFERENCES students (ulearnID)")
                .append(");")
                .toString();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void saveModules(Collection<Module> modules){
        String sql = "INSERT INTO course(moduleName) VALUES(?)";

        for (Module m : modules){
            try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1,m.getName());
                pstmt.executeUpdate();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void saveStudents(Collection<Student> students) {
        String sql = "INSERT INTO students(ulearnID,fullName,vkID,status,city) VALUES(?,?,?,?,?)";

        for (Student s : students){
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1,s.getUlearnID());
                pstmt.setString(2,s.getFullName());
                pstmt.setInt(3,s.getVkID()==null?-1:s.getVkID());
                pstmt.setString(4,s.getStatus());
                pstmt.setString(5,s.getCity());
                pstmt.executeUpdate();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void saveModuleScores(Course course) {
        String sql = "INSERT INTO moduleScores(moduleID,studentUlearnID,questionsScore,exercisesScore,practiceScore) VALUES(?,?,?,?,?)";

        String findPrimarySql = "SELECT id FROM course WHERE moduleName='";

        for (Module m : course.getModules()){
            for (Student s : course.getStudentIDs().stream().map(course::getStudent).toList()){
                ModuleScores ms = m.getScores(s);
                try (Connection conn = DriverManager.getConnection(URL);
                     PreparedStatement pstmt = conn.prepareStatement(sql);
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(findPrimarySql + m.getName() + "'")){
                    rs.next();
                    pstmt.setInt(1,rs.getInt("id"));
                    pstmt.setString(2,s.getUlearnID());
                    pstmt.setInt(3,ms.getQuestionsScore());
                    pstmt.setInt(4,ms.getExercisesScore());
                    pstmt.setInt(5,ms.getPracticeScore());
                    pstmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static List<Module> getModules(){
        String sql = "SELECT moduleName FROM course";

        List<Module> res = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                res.add(new Module(rs.getString("moduleName")));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return res;
    }

    public static List<Student> getStudents(){
        String sql = "SELECT * FROM students";

        List<Student> res = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                Student s = new Student(rs.getString("ulearnID"),rs.getString("fullName"));
                s.setStatus(rs.getString("status"));
                s.setCity(rs.getString("city"));
                int vkID = rs.getInt("vkID");
                s.setVkID(vkID==-1?null:vkID);
                res.add(s);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return res;
    }

    public static Course getCourse(String name){
        String msFormat = "SELECT * FROM moduleScores WHERE studentUlearnID='%s' AND moduleID=%d";
        String findPrimaryFormat = "SELECT id FROM course WHERE moduleName='&s'";

        Course c = new Course(name);
        List<Student> students = DBRepository.getStudents();
        List<Module> modules = DBRepository.getModules();
        for (Module m : modules){

            int moduleID = 0;
            try (Connection conn = DriverManager.getConnection(URL);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(String.format(findPrimaryFormat,m.getName()))) {
                rs.next();
                moduleID = rs.getInt("id");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            for (Student s:students){
                try (Connection conn = DriverManager.getConnection(URL);
                     Statement stmt = conn.createStatement();
                     ResultSet rsCourse = stmt.executeQuery(String.format(msFormat,s.getUlearnID(),moduleID))) {
                    rsCourse.next();
                    m.addStudent(s,new ModuleScores(rsCourse.getInt("questionsScore"),
                            rsCourse.getInt("exercisesScore"),
                            rsCourse.getInt("practiceScore")));

                    c.addStudent(s);
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            }

            c.addModule(m);
        }
        return c;
    }
}

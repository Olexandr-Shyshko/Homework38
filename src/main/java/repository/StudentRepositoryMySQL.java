package repository;

import domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryMySQL implements StudentRepository {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/homework38";
    private static final String DB_USER = "bestuser";
    private static final String DB_PASSWORD = "bestuser";
    private static final String SQL_SAVE = "insert into student (name,course,group_name) values (?,?,?)";
    private static final String SQL_FIND_ALL_STUD = "select * from student";
    private static final String SQL_DEL_STUD_BY_ID = "delete from student where id = ?";

    @Override
    public void saveStudent(Student s) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            con.setAutoCommit(false);
            ps = con.prepareStatement(SQL_SAVE);
            ps.setString(1, s.getName());
            ps.setInt(2, s.getCourse());
            ps.setString(3, s.getGroupName());
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
        } finally {
            try {
                assert con != null;
                con.close();
                assert ps != null;
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public List<Student> findAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_STUD);
            while (resultSet.next()) {
                Student student = Student.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .course(resultSet.getInt("course"))
                        .groupName(resultSet.getString("group_name"))
                        .build();
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public void deleteStudentById(int id) {
        PreparedStatement ps = null;
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            ps = con.prepareStatement(SQL_DEL_STUD_BY_ID);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

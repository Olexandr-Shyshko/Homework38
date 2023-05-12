package repository;

import domain.Student;

import java.util.List;

public interface StudentRepository {
    void saveStudent (Student s);
    List<Student> findAllStudents();
    void deleteStudentById (int id);
}

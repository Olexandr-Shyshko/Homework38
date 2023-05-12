import domain.Student;
import repository.StudentRepository;
import repository.StudentRepositoryMySQL;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepositoryMySQL();
        Student st1 = Student.builder()
                .name("Ivan")
                .course(3)
                .groupName("UDF-05")
                .build();
        Student st2 = Student.builder()
                .name("Mariya")
                .course(1)
                .groupName("FM-03")
                .build();

        studentRepository.saveStudent(st1);
        studentRepository.saveStudent(st2);

        List<Student> studentList = studentRepository.findAllStudents();
        System.out.println(studentList);

        studentRepository.deleteStudentById(2);

        studentList = studentRepository.findAllStudents();
        System.out.println(studentList);
    }
}

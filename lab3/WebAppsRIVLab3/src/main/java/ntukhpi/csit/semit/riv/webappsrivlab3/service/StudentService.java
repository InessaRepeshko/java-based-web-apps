package ntukhpi.csit.semit.riv.webappsrivlab3.service;

import ntukhpi.csit.semit.riv.webappsrivlab3.model.student.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    List<Student> getFilteredAndSortedStudents(String search,
                                               String fundingType,
                                               String scholarshipStatus,
                                               String sort);

    void saveAllStudents(List<Student> students);

    Student findStudentById(Long id);

    Student findStudentByExample(Student student);

    Student saveStudent(Student student);

    Student updateStudent(Student student);

    void deleteStudent(Student student);
}

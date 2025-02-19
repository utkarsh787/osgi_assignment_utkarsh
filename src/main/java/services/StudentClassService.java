package services;


import com.ttn.demo.core.models.Students;

import java.util.List;

public interface StudentClassService {
      String addStudent(Students student);
    String deleteStudent(int id);
    boolean isStudentPassed(int id);
    Students getStudent(int id);
      List<Students> getAllStudents();
}


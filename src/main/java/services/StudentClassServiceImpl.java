package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ttn.demo.core.models.Students;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = StudentClassService.class)
public class StudentClassServiceImpl implements StudentClassService {

    // In-memory list to hold students
    private final List<Students> students = new ArrayList<>();

    @Reference
    private ClassConfigurationService classConfigurationService;

    @Override
    public String addStudent(Students student) {
        // Check if adding the student is allowed using the configuration service.
        if (classConfigurationService.isClassLimitReached(students)) {
            students.add(student);
        } else {
            System.out.println("Cannot add student. Class limit reached.");
        }
        return "Student added :" + student.getName();
    }

    @Override
    public String deleteStudent(int id) {
        Optional<Students> studentToRemove = students.stream().filter(s -> s.getId() == id).findFirst();
        if (studentToRemove.isPresent()) {
            students.remove(studentToRemove.get());
            return "Student removed successfully";
        }
        return "Student not found";
    }

    @Override
    public boolean isStudentPassed(int id) {
        Students student = getStudent(id);
        if (student != null) {
            return student.getMarks() >= classConfigurationService.getPassingMarks();
        }
        return false;
    }

    @Override
    public Students getStudent(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Students> getAllStudents() {
        return new ArrayList<>(students);
    }
}

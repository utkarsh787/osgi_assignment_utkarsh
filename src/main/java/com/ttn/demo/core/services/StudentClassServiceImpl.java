package com.ttn.demo.core.services;

import java.util.ArrayList;
import java.util.List;

import com.ttn.demo.core.models.Students;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = StudentClassService.class)
public class StudentClassServiceImpl implements StudentClassService {

    // In-memory list to hold students
    private final List<Students> students = new ArrayList<>();
    public StudentClassServiceImpl() {
        students.add(new Students(1, "Utkarsh", 85, 26));
        students.add(new Students(2, "Adarsh", 35, 16));
        students.add(new Students(3, "Advit", 20, 25));
        students.add(new Students(4, "Pallavi", 80, 22));
        students.add(new Students(5, "Rohit", 79, 21));
        students.add(new Students(6, "Sharma", 10, 21));
        students.add(new Students(7, "sachin", 18, 95));
        students.add(new Students(8, "Pawan", 85, 25));

    }

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
        return students.removeIf(s -> s.getId() == id) ? "Student removed successfully" : "Student not found";
    }

    @Override
    public boolean isStudentPassed(int id) {
        return students.stream().anyMatch(s -> s.getId() == id && s.getMarks() >= 40);
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

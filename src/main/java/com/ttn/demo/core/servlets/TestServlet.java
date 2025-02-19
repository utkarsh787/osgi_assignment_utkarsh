package com.ttn.demo.core.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.ttn.demo.core.models.Students;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ttn.demo.core.services.StudentClassService;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/ttn")
public class TestServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServlet.class);
    @Reference
    private StudentClassService studentClassService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application");
        resp.setCharacterEncoding("UTF-8");

        // Ensure the service is available
        if (studentClassService == null) {
            resp.getWriter().write("{\"error\": \"Student service is not available\"}");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            resp.getWriter().write("{\"error\": \"Action parameter is required\"}");
            return;
        }

        try {
            switch (action.toLowerCase()) {
                case "add":
                    Students student = new Students(
                            Integer.parseInt(req.getParameter("id")),
                            req.getParameter("name"),
                            Integer.parseInt(req.getParameter("marks")),
                            Integer.parseInt(req.getParameter("age"))
                    );
                    resp.getWriter().write("{\"message\": \"" + studentClassService.addStudent(student) + "\"}");
                    break;

                case "delete":
                    int deleteId = Integer.parseInt(req.getParameter("id"));
                    resp.getWriter().write("{\"message\": \"" + studentClassService.deleteStudent(deleteId) + "\"}");
                    break;

                case "ispassed":
                    int passId = Integer.parseInt(req.getParameter("id"));
                    resp.getWriter().write("{\"result\": \"" + (studentClassService.isStudentPassed(passId) ? "Student Passed" : "Student Failed") + "\"}");
                    break;

                case "get":
                    int getId = Integer.parseInt(req.getParameter("id"));
                    Students retrievedStudent = studentClassService.getStudent(getId);
                    resp.getWriter().write(retrievedStudent != null ? "{\"name\": \"" + retrievedStudent.getName() + "\"}" : "{\"error\": \"Student not found\"}");
                    break;

                case "getall":
                    List<Students> students = studentClassService.getAllStudents();
                    String studentNames = students.stream().map(Students::getName).collect(Collectors.joining(", "));
                    resp.getWriter().write("{\"students\": \"" + studentNames + "\"}");
                    break;

                default:
                    resp.getWriter().write("{\"error\": \"Invalid action\"}");
                    break;
            }
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\": \"Invalid numeric parameter\"}");
        }
    }
}
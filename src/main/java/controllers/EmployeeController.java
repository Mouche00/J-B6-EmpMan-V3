package controllers;

import daos.implementations.EmployeeDAOImpl;
import models.Department;
import models.Employee;
import services.implementations.EmployeeServiceImpl;
import services.interfaces.EmployeeService;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

@WebServlet("/employee")
public class EmployeeController extends HttpServlet {
    @Inject
    private EmployeeService employeeService;
    private final String path = "/WEB-INF/views/";


//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String fullUri = request.getRequestURI();
//        String contextPath = request.getContextPath();
//        String servletPath = request.getServletPath();
//
//        String uriAfterServletUrl = fullUri.substring(contextPath.length() + servletPath.length());
//
//        switch (uriAfterServletUrl) {
//            case "/save":
//                save(request, response);
//                break;
//            case "/delete":
//                delete(request, response);
//                break;
//            case "/update":
//                update(request, response);
//                break;
//            default:
//                throw new ServletException("Resource not found.");
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String uriAfterServletUrl = fullUri.substring(contextPath.length() + servletPath.length());

        switch (uriAfterServletUrl) {
            case "/create":
                create(request, response);
                break;
            case "/edit":
                edit(request, response);
                break;
            default:
                list(request, response);
        }
    }

    private boolean setParameter(HttpServletRequest request, String parameter, Consumer<String> setter) {
        String value = request.getParameter(parameter);
        boolean canBeSet = value != null;
        if (canBeSet) {
            setter.accept(value);
        }
        return canBeSet;
    }


//    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession();
//        Employee employee = (Employee) session.getAttribute("employee");
//
//        setParameter(request,"name", employee::setName);
//        setParameter(request,"email", employee::setEmail);
//        setParameter(request,"phone", employee::setPhone);
//        setParameter(request,"post", employee::setPost);
//        setParameter(request,"department", departmentId -> employee.setDepartment(getDepartment(departmentId)));
//
//        employeeService.update(employee);
//        addMessage(request, employee.getName() + " updated successfully!");
//        response.sendRedirect("/employees");
//    }
//
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Optional<Employee> employee = employeeService.find(id);
        employee.ifPresent(e -> {
            request.setAttribute("employee", e);
            HttpSession session = request.getSession();
            session.setAttribute("employee", e);
            session.setMaxInactiveInterval(10*60);
        });
        HttpSession session = request.getSession();
        List<Employee> employees = (List<Employee>) session.getAttribute("employees");
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }
//
//    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String id = request.getParameter("id");
//        employeeService.delete(id);
//        addMessage(request,  "Employee deleted successfully!");
//        response.sendRedirect("/employees");
//    }

    private Map<UUID, Department> getDepartments() {
        ServletContext context = getServletContext();
        return (Map<UUID, Department>) context.getAttribute("departments");
    }

    private Department getDepartment(String id) {
        return (Department) getDepartments().get(UUID.fromString(id));
    }

//    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String name = request.getParameter("name");
//        String email = request.getParameter("email");
//        String phone = request.getParameter("phone");
//        String post = request.getParameter("post");
//        String departmentId = request.getParameter("department");
//
//        List<String> errorMessages = new ArrayList<>();
//
//        // Manual validation checks
//        if (name == null || name.trim().isEmpty()) {
//            errorMessages.add("Name cannot be empty");
//        }
//        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//            errorMessages.add("Email should be valid");
//        }
//        if (phone == null || phone.trim().isEmpty()) {
//            errorMessages.add("Phone cannot be empty");
//        }
//        if (post == null || post.trim().isEmpty()) {
//            errorMessages.add("Post cannot be empty");
//        }
//        if (getDepartment(departmentId) == null) {
//            errorMessages.add("Department cannot be null");
//        }
//
//        if (errorMessages.isEmpty()) {
//            Employee employee = new Employee();
//            employeeService.save(employee);
//            addMessage(request, employee.getName() +" added successfully!");
//        } else {
//            addMessage(request, "Failed to save employee");
//        }
//
//        response.sendRedirect("/employees");
//    }
//
//    private void addMessage(HttpServletRequest request, String message) {
//        HttpSession session = request.getSession();
//        List<String> messages = (List<String>) session.getAttribute("messages");
//        if (messages == null) {
//            messages = new ArrayList<>();
//        }
//        messages.add(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")
//) + ": " + message);
//
//        // Keep only the last three messages
//        if (messages.size() > 3) {
//            messages = messages.subList(messages.size() - 3, messages.size());
//        }
//
//        session.setAttribute("messages", messages);
//    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("departments", getDepartments());
//        request.getRequestDispatcher("/views/form.jsp").forward(request, response);
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("s");
        String filter = request.getParameter("dep");
        List<Employee> employees = new ArrayList<>();
        if (!(searchTerm == null || searchTerm.isEmpty())) {
            employees = employeeService.findAll(searchTerm);
        } else if(!(filter == null || filter.isEmpty())) {
            employees = employeeService.filter(null, filter);
        } else {
            employees = employeeService.getAll();
        }

        HttpSession session = request.getSession();
        session.setAttribute("employees", employees);
        session.setAttribute("employee", null);

        request.setAttribute("employees", employees);
//        request.setAttribute("departments", getDepartments());
        request.getRequestDispatcher(path + "employee.jsp").forward(request, response);
    }
}

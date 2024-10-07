package controllers;

import DAOs.implementations.EmployeeDAOImpl;
import models.Department;
import models.Employee;
import services.implementations.EmployeeServiceImpl;
import services.interfaces.EmployeeService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@WebServlet
public class EmployeeController extends HttpServlet {
    private EmployeeService employeeService;

    @Override
    public void init() {
        employeeService = new EmployeeServiceImpl(
                new EmployeeDAOImpl());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String uriAfterServletUrl = fullUri.substring(contextPath.length() + servletPath.length());

        switch (uriAfterServletUrl) {
            case "/save":
                save(request, response);
                break;
            case "/delete":
                delete(request, response);
                break;
            case "/update":
                update(request, response);
                break;
            default:
                throw new ServletException("Resource not found.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String uriAfterServletUrl = fullUri.substring(contextPath.length() + servletPath.length());

        switch (uriAfterServletUrl) {
            case "/":
                list(request, response);
                break;
            case "/create":
                create(request, response);
                break;
            case "/edit":
                edit(request, response);
                break;
            default:
                throw new ServletException("Resource not found.");
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


    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");

        setParameter(request,"name", employee::setName);
        setParameter(request,"email", employee::setEmail);
        setParameter(request,"phone", employee::setPhone);
        setParameter(request,"post", employee::setPost);
        setParameter(request,"department", departmentId -> employee.setDepartment(getDepartment(departmentId)));

        employeeService.update(employee);
        response.sendRedirect("/");
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Optional<Employee> employee = employeeService.find(id);
        employee.ifPresent(e -> {
            request.setAttribute("employee", e);
            HttpSession session = request.getSession();
            session.setAttribute("employee", e);
            session.setMaxInactiveInterval(10*60);
        });
        request.getRequestDispatcher("/views/form.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        employeeService.delete(id);
        response.sendRedirect("/");
    }

    private Map<UUID, Department> getDepartments() {
        ServletContext context = getServletContext();
        return (Map<UUID, Department>) context.getAttribute("departments");
    }

    private Department getDepartment(String id) {
        return (Department) getDepartments().get(UUID.fromString(id));
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<UUID, Department> departments = getDepartments();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String post = request.getParameter("post");
        String departmentId = request.getParameter("department");
        employeeService.save(new Employee(name, email, phone, post, getDepartment(departmentId)));
        response.sendRedirect("/");
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("departments", getDepartments());
        request.getRequestDispatcher("/views/form.jsp").forward(request, response);
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("s");
        List<Employee> employees = new ArrayList<>();
        if(searchTerm == null || searchTerm.isEmpty()) {
            employees = employeeService.getAll();
        } else {
            employees = employeeService.findAll(searchTerm);
        }
        request.setAttribute("employees", employees);
        request.setAttribute("departments", getDepartments());
        request.getRequestDispatcher("/views/index.jsp").forward(request, response);
    }
}

package controllers;

import daos.implementations.DepartmentDAOImpl;
import daos.implementations.EmployeeDAOImpl;
import daos.implementations.LeaveDAOImpl;
import models.Department;
import models.Employee;
import models.Leave;
import models.User;
import services.implementations.EmployeeServiceImpl;
import services.implementations.LeaveServiceImpl;
import services.interfaces.DepartmentService;
import services.interfaces.EmployeeService;
import services.interfaces.LeaveService;
import services.interfaces.UserService;
import utils.JPAUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/leave")
public class LeaveController extends HttpServlet {
    @Inject
    private LeaveService leaveService;

    @Inject
    private EmployeeService employeeService;

    @Inject
    private UserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        Employee employee = employeeService.find("85f616af-5a4b-46ea-924e-7bae0fdc8282").get();

        Leave leave = new Leave(LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-15"), "Absence", LocalDate.parse("2024-10-10"), employee);
        leaveService.save(leave);
    }
}

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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/leave/*")
public class LeaveController extends HttpServlet {
    @Inject
    private LeaveService leaveService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

//        Employee employee = employeeService.find("85f616af-5a4b-46ea-924e-7bae0fdc8282").get();
//
//        Leave leave = new Leave(LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-15"), "Absence", LocalDate.parse("2024-10-10"), employee);
//        leaveService.save(leave);
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
    //                delete(request, response);
                break;
            case "/validate":
                    validate(request, response);
                break;
            default:
                throw new ServletException("Resource not found.");
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDate = request.getParameter("start-date");
        String endDate = request.getParameter("end-date");
        String reason = request.getParameter("reason");

        HttpSession session = request.getSession();

        leaveService.save(new Leave(LocalDate.parse(startDate), LocalDate.parse(endDate), reason, (User) session.getAttribute("loggedUser")));

        response.sendRedirect("/employee");
    }

    private void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");

        leaveService.validate(id);

        response.sendRedirect("/employee");
    }
}

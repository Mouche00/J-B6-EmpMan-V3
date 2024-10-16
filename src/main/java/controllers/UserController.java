package controllers;

import models.Employee;
import models.Leave;
import models.User;
import services.interfaces.EmployeeService;
import services.interfaces.LeaveService;
import services.interfaces.UserService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/user")
public class UserController extends HttpServlet {
    @Inject
    private LeaveService leaveService;

    @Inject
    private UserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        User user = userService.find("5dfa0617-c88f-4d14-85e2-26c4b009a8a9").get();
        user.setSalary(10000);
        user.setChildren(1);
        userService.update(user);
        userService.getSalaryHistory("5dfa0617-c88f-4d14-85e2-26c4b009a8a9");

    }
}

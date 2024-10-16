package controllers;

import models.Employee;
import models.Leave;
import models.User;
import services.interfaces.LeaveService;
import services.interfaces.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private final String path = "/WEB-INF/views/";

    @Inject
    private UserService userService;

    @Inject
    private LeaveService leaveService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String uriAfterServletUrl = fullUri.substring(contextPath.length() + servletPath.length());

        switch (uriAfterServletUrl) {
            case "/create":
//                create(request, response);
                break;
            case "/edit":
//                edit(request, response);
                break;
            default:
                list(request, response);
        }
    }

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("s");
        List<User> users = new ArrayList<>();
        if (!(searchTerm == null || searchTerm.isEmpty())) {
            users = userService.findAll(searchTerm);
        } else {
            users = userService.getAll();
        }

        request.setAttribute("users", users);
        request.setAttribute("leaves", leaveService.getAll());
        request.getRequestDispatcher(path + "admin.jsp").forward(request, response);
    }
}
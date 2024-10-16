package controllers;

import models.Admin;
import models.Recruiter;
import models.User;
import services.interfaces.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/*")
public class LoginController extends HttpServlet {
    private final String path = "/WEB-INF/views/";

    @Inject
    private UserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fullUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();

        String uriAfterServletUrl = fullUri.substring(contextPath.length() + servletPath.length());

        switch (uriAfterServletUrl) {
            case "/login":
                request.getRequestDispatcher(path + "login.jsp").forward(request, response);
                break;
            case "/logout":
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", null);
                session.setAttribute("role", null);
                response.sendRedirect("/auth/login");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Optional<User> user = userService.findByEmail(email);

        if (!user.isEmpty() && user.get().getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user.get());

            if (user.get() instanceof Admin) {
                session.setAttribute("role", "admin");
                response.sendRedirect("/admin");
            } else if (user.get() instanceof Recruiter) {
                session.setAttribute("role", "recruiter");
                response.sendRedirect("/recruiter");
            } else {
                session.setAttribute("role", "employee");
                response.sendRedirect("/employee");
            }

        } else {
            response.sendRedirect("/auth/login");
        }
    }
}

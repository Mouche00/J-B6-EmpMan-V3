package controllers;

import DAOs.implementations.DepartmentDAOImpl;
import models.Department;
import services.implementations.DepartmentServiceImpl;
import services.interfaces.DepartmentService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DepartmentController extends HttpServlet {
    private DepartmentService departmentService;

    @Override
    public void init() {
        departmentService = new DepartmentServiceImpl(
                new DepartmentDAOImpl()
        );
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");

        departmentService.save(new Department(name));

        response.sendRedirect("/employees");
    }
}

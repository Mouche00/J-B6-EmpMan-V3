package filters;

import DAOs.implementations.DepartmentDAOImpl;
import models.Department;
import services.implementations.DepartmentServiceImpl;
import services.interfaces.FetchService;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CacheFilter implements Filter {
    private FetchService<Department> departmentService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        departmentService = new DepartmentServiceImpl(new DepartmentDAOImpl());

        Map<UUID, Department> departments = departmentService.getAll().stream().collect(Collectors.toMap(Department::getId, department -> department));
        filterConfig.getServletContext().setAttribute("departments", departments);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}

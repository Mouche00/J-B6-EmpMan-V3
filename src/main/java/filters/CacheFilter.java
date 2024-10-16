//package filters;
//
//import daos.implementations.DepartmentDAOImpl;
//import models.Department;
//import services.interfaces.DepartmentService;
//
//import javax.inject.Inject;
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//import java.util.Map;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@WebFilter("/*")
//public class CacheFilter implements Filter {
//    @Inject
//    private DepartmentService departmentService;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Map<UUID, Department> departments = departmentService.getAll().stream().collect(Collectors.toMap(Department::getId, department -> department));
//        filterConfig.getServletContext().setAttribute("departments", departments);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        chain.doFilter(request, response);
//    }
//}

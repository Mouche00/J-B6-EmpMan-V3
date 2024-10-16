//package listeners;
//
//import models.Department;
//import services.interfaces.DepartmentService;
//import services.interfaces.JobOfferService;
//
//import javax.inject.Inject;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.stream.Collectors;
//
//@WebListener
//public class DepartmentCacheListener implements ServletContextListener {
//
//    @Inject
//    private DepartmentService departmentService;
//
//    @Override
//    public void contextInitialized(ServletContextEvent event) {
//        Map<UUID, Department> departments = departmentService.getAll().stream().collect(Collectors.toMap(Department::getId, department -> department));
////        event.getServletContext().setAttribute("departments", departments);
//
//        System.out.println("Department list initialized.");
//    }
//}

//import daos.implementations.EmployeeDAOImpl;
//import daos.implementations.LeaveDAOImpl;
//import models.Admin;
//import models.Department;
//import models.Employee;
//import models.Leave;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import services.implementations.EmployeeServiceImpl;
//import services.implementations.LeaveServiceImpl;
//import services.interfaces.EmployeeService;
//import services.interfaces.LeaveService;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class LeaveTest {
//
//    private EntityManagerFactory emf;
//    private EntityManager em;
//    private EmployeeService employeeService;
//    private LeaveService leaveService;
//
//    @Before
//    public void setUp() {
//        // Initialize EntityManagerFactory and EntityManager
//        emf = Persistence.createEntityManagerFactory("test-unit");
//        em = emf.createEntityManager();
//        employeeService = new EmployeeServiceImpl(new EmployeeDAOImpl());
//        leaveService = new LeaveServiceImpl(new LeaveDAOImpl());
//    }
//
//    @After
//    public void tearDown() {
//        // Close EntityManager and EntityManagerFactory
//        if (em != null) em.close();
//        if (emf != null) emf.close();
//        if(employeeService != null) employeeService = null;
//        if(leaveService != null) leaveService = null;
//    }
//
//    @Test
//    public void testPersistLeave() {
//
//        // Create Department
//        Department department = new Department("IT");
//
//        // Create and persist Employee
////        Employee employee = new Employee(
////                "John Doe",
////                "555-1234",
////                "123 Elm St, Springfield, IL",
////                "johndoe@example.com",
////                "password123",
////                LocalDate.of(1990, 5, 15), // Date of Birth
////                "123-45-6789",              // SSN
////                LocalDate.of(2020, 1, 10), // Hiring Date
////                60000.00,                   // Salary
////                2,                          // Number of Children
////                15,                         // Leave Balance
////                "Software Engineer",
////                department
////        );
////        employeeService.save(employee);
//
//        // Create and persist Leave
////        Leave leave = new Leave(LocalDate.parse("2024-10-11"), LocalDate.parse("2024-10-12"), "Absence", LocalDate.parse("2024-10-10"), employee);
////        leaveService.save(leave);
//
//
//        // Fetch employee and verify
////        Employee foundEmployee = employeeService.find(employee.getId().toString()).get();
//
////        assertNotNull(foundEmployee);
////        assertEquals("John Doe", foundEmployee.getName());
////        assertEquals(department, foundEmployee.getDepartment());
//    }
//}

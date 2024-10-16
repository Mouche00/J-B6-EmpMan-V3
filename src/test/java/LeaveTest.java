import daos.implementations.AdminDAOImpl;
import daos.implementations.EmployeeDAOImpl;
import daos.implementations.LeaveDAOImpl;
import daos.implementations.UserDAOImpl;
import daos.interfaces.AdminDAO;
import daos.interfaces.EmployeeDAO;
import daos.interfaces.LeaveDAO;
import daos.interfaces.UserDAO;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.implementations.EmployeeServiceImpl;
import services.implementations.LeaveServiceImpl;
import services.interfaces.EmployeeService;
import services.interfaces.LeaveService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class LeaveTest {

    private EmployeeDAO employeeDAO;
    private AdminDAO adminDAO;
    private UserDAO userDAO;
    private LeaveDAO leaveDAO;

    @Before
    public void setUp() {
        employeeDAO = new EmployeeDAOImpl();
        userDAO = new UserDAOImpl();
        leaveDAO = new LeaveDAOImpl();
        adminDAO = new AdminDAOImpl();
    }

    @After
    public void tearDown() {
        if(employeeDAO != null) employeeDAO = null;
        if(leaveDAO != null) leaveDAO = null;
    }

    @Test
    public void testPersistLeave() {

        // Create Department
        Department department = new Department("IT");

        // Create and persist Employee
        Employee employee = new Employee(
                "Admin",
                "555-1234",
                "123 Elm St, Springfield, IL",
                "employee@example.com",
                "password",
                LocalDate.of(1990, 5, 15), // Date of Birth
                "123-45-6789",              // SSN
                LocalDate.of(2020, 1, 10), // Hiring Date
                6000.00,                   // Salary
                2,                          // Number of Children
                15,                         // Leave Balance
                "Software Engineer",
                department
        );

        employeeDAO.save(employee);

        Admin admin = new Admin(
                "Admin",
                "555-1234",
                "123 Elm St, Springfield, IL",
                "admin@example.com",
                "password",
                LocalDate.of(1990, 5, 15), // Date of Birth
                "123-45-6784",              // SSN
                LocalDate.of(2020, 1, 10), // Hiring Date
                10000.00,                   // Salary
                5,                          // Number of Children
                15
        );
        adminDAO.save(admin);

//         Create and persist Leave
//        Leave leave = new Leave(LocalDate.parse("2024-10-11"), LocalDate.parse("2024-10-12"), "Absence", LocalDate.parse("2024-10-10"), employee);
//        leaveService.save(leave);


//         Fetch employee and verify
        User user = userDAO.findByEmail("admin@example.com").get();

        assertNotNull(user);
        assertEquals("Admin", user.getName());
        assertTrue(user instanceof Admin);
    }
}

import daos.implementations.EmployeeDAOImpl;
import daos.implementations.UserDAOImpl;
import daos.interfaces.EmployeeDAO;
import daos.interfaces.UserDAO;
import models.Admin;
import models.Department;
import models.Employee;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EmployeeTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private UserDAO userDAO;
    private EmployeeDAO employeeDAO;

    @Before
    public void setUp() {
        // Initialize EntityManagerFactory and EntityManager
        userDAO = new UserDAOImpl();
        employeeDAO = new EmployeeDAOImpl();
    }

    @Test
    public void testPersistEmployee() {

        // Fetch employee and verify
//        employeeDAO.save(new Employee());
        User foundEmployee = userDAO.find(UUID.fromString("d71f4c66-e944-4194-b66e-b34b2a639bf1")).get();

        assertNotNull(foundEmployee);
//        assertEquals("John Doe", foundEmployee.getName());
//        assertEquals(department, foundEmployee.getDepartment());
    }

}

//import models.Admin;
//import models.Department;
//import models.Employee;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//
//public class EmployeeTest {
//
//    private EntityManagerFactory emf;
//    private EntityManager em;
//
//    @Before
//    public void setUp() {
//        // Initialize EntityManagerFactory and EntityManager
//        emf = Persistence.createEntityManagerFactory("test-unit");
//        em = emf.createEntityManager();
//    }
//
//    @After
//    public void tearDown() {
//        // Close EntityManager and EntityManagerFactory
//        if (em != null) em.close();
//        if (emf != null) emf.close();
//    }
//
//    @Test
//    public void testPersistEmployee() {
//        // Start transaction
//        em.getTransaction().begin();
//
//        // Create Department
//        Department department = new Department("IT");
//        em.persist(department);
//
//        // Create and persist Employee
//        Employee employee = new Employee();
////        employee.setDOB(LocalDate.of(1990, 5, 14));
////        employee.setSalary(55000);
//        em.persist(employee);
//
//        // Commit transaction
//        em.getTransaction().commit();
//
//        // Fetch employee and verify
//        Employee foundEmployee = em.find(Employee.class, employee.getId());
//
//        assertNotNull(foundEmployee);
//        assertEquals("John Doe", foundEmployee.getName());
////        assertEquals(department, foundEmployee.getDepartment());
//    }
//
//    @Test
//    public void testPersistAdmin() {
//        // Start transaction
//        em.getTransaction().begin();
//
//        // Create Department
//        Department department = new Department("Admin");
//        em.persist(department);
//
//        // Create and persist Admin
//        Admin admin = new Admin();
//        admin.setName("Jane Doe");
//        admin.setEmail("jane@example.com");
//        admin.setPhone("555-5678");
//        admin.setPost("Admin");
//        em.persist(admin);
//
//        // Commit transaction
//        em.getTransaction().commit();
//
//        // Fetch admin and verify
//        Admin foundAdmin = em.find(Admin.class, admin.getId());
//        assertNotNull(foundAdmin);
//        assertEquals("Jane Doe", foundAdmin.getName());
//        assertEquals("Admin", foundAdmin.getPost());
//    }
//
//    @Test
//    public void testFindAllEmployees() {
//        // Query all employees
//        List<Employee> employees = em.createQuery("FROM Employee", Employee.class).getResultList();
//
//        // Verify the result
//        assertNotNull(employees);
//        assertTrue(employees.size() > 0);
//    }
//}

//package services.implementations;
//
//import daos.interfaces.EmployeeDAO;
//import models.Employee;
//import models.Department;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class EmployeeServiceImplTest {
//
//    @Mock
//    private EmployeeDAO employeeDAO;
//
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
//
//    private Employee employee1;
//    private Employee employee2;
//
//    @BeforeEach
//    void setUp() {
//
//        // Initialize sample data
//        Department department1 = new Department();
//        department1.setName("HR");
//
//        Department department2 = new Department();
//        department2.setName("Engineering");
//
//        employee1 = new Employee();
//        employee1.setId(UUID.randomUUID());
//        employee1.setPost("Manager");
//        employee1.setDepartment(department1);
//
//        employee2 = new Employee();
//        employee2.setId(UUID.randomUUID());
//        employee2.setPost("Engineer");
//        employee2.setDepartment(department2);
//    }
//
//    @Test
//    void testFindAll_withSearchTerm_shouldReturnFilteredResults() {
//        // Arrange
//        String searchTerm = "John";
//        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
//
//        when(employeeDAO.findAll(searchTerm)).thenReturn(mockEmployees);
//
//        // Act
//        List<Employee> result = employeeService.findAll(searchTerm);
//
//        // Assert
//        assertEquals(mockEmployees, result); // The result should match the mocked data
//        verify(employeeDAO, times(1)).findAll(searchTerm); // Verify that employeeDAO.findAll was called once
//    }
//
//    @Test
//    void testFilter_withPostAndDepartment_shouldReturnFilteredEmployees() {
//        // Arrange
//        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
//        when(employeeDAO.getAll()).thenReturn(mockEmployees);
//
//        // Act
//        List<Employee> result = employeeService.filter("Manager", "HR");
//
//        // Assert
//        assertEquals(1, result.size()); // Only one employee matches the filters
//        assertEquals(employee1, result.get(0)); // The first employee should match
//        verify(employeeDAO, times(1)).getAll(); // Ensure the DAO method was called once
//    }
//
//    @Test
//    void testFilter_withPostOnly_shouldReturnPostFilteredEmployees() {
//        // Arrange
//        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
//        when(employeeDAO.getAll()).thenReturn(mockEmployees);
//
//        // Act
//        List<Employee> result = employeeService.filter("Engineer", null);
//
//        // Assert
//        assertEquals(1, result.size()); // Only one employee matches the "Engineer" post
//        assertEquals(employee2, result.get(0)); // The second employee should match
//        verify(employeeDAO, times(1)).getAll(); // Ensure the DAO method was called once
//    }
//
//    @Test
//    void testFilter_withDepartmentOnly_shouldReturnDepartmentFilteredEmployees() {
//        // Arrange
//        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
//        when(employeeDAO.getAll()).thenReturn(mockEmployees);
//
//        // Act
//        List<Employee> result = employeeService.filter(null, "HR");
//
//        // Assert
//        assertEquals(1, result.size()); // Only one employee belongs to the HR department
//        assertEquals(employee1, result.get(0)); // The first employee should match
//        verify(employeeDAO, times(1)).getAll(); // Ensure the DAO method was called once
//    }
//
//    @Test
//    void testFilter_withNoFilters_shouldReturnAllEmployees() {
//        // Arrange
//        List<Employee> mockEmployees = Arrays.asList(employee1, employee2);
//        when(employeeDAO.getAll()).thenReturn(mockEmployees);
//
//        // Act
//        List<Employee> result = employeeService.filter(null, null);
//
//        // Assert
//        assertEquals(2, result.size()); // Both employees should be returned
//        assertEquals(mockEmployees, result); // The result should match the mocked employees list
//        verify(employeeDAO, times(1)).getAll(); // Ensure the DAO method was called once
//    }
//}
package services.interfaces;

import models.Department;
import models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService extends GenericService<Employee, String> {
    List<Employee> findAll(String searchTerm);
    List<Employee> filter(String post, String department);
}

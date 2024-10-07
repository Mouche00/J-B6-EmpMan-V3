package services.interfaces;

import models.Department;
import models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    void save(Employee employee);
    void update(Employee employee);
    void delete(String id);
    Optional<Employee> find(String id);
    List<Employee> getAll();
    List<Employee> findAll(String searchTerm);
    List<Employee> filter(String post, Department department);
}

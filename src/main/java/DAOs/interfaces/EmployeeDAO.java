package DAOs.interfaces;

import models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO extends FetchDAO<Employee> {
    void save(Employee employee);
    void update(Employee employee);
    void delete(Employee employee);
    Optional<Employee> find(String id);
    List<Employee> findAll(String searchTerm);
}

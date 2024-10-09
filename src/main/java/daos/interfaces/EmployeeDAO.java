package daos.interfaces;

import models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO extends GenericDAO<Employee> {
    List<Employee> findAll(String searchTerm);
}

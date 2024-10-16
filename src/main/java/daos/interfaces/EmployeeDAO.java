package daos.interfaces;

import models.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeDAO extends GenericDAO<Employee, UUID> {
    List<Employee> findAll(String searchTerm);
}

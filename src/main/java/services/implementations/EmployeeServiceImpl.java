package services.implementations;

import DAOs.interfaces.EmployeeDAO;
import models.Department;
import models.Employee;
import services.interfaces.EmployeeService;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl  implements EmployeeService {
    private final EmployeeDAO employeeDAO;
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void save(Employee employee) {
        employeeDAO.save(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeDAO.update(employee);
    }

    @Override
    public void delete(String id) {
        Optional<Employee> employee = find(id);
        employee.ifPresent(employeeDAO::delete);
    }

    @Override
    public Optional<Employee> find(String id) {
        return employeeDAO.find(id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    @Override
    public List<Employee> findAll(String searchTerm) {
        return employeeDAO.findAll(searchTerm);
    }

    @Override
    public List<Employee> filter(String post, Department department) {
        List<Employee> employees = employeeDAO.getAll();
        if(post != null) {
            employees = employees.stream().filter(e -> e.getPost().contains(post)).toList();
        }

        if(department != null) {
            employees = employees.stream().filter(e -> e.getDepartment().equals(department)).toList();
        }
        return employees;
    }
}

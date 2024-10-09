package services.implementations;

import daos.interfaces.EmployeeDAO;
import daos.interfaces.GenericDAO;
import models.Employee;
import services.interfaces.EmployeeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String> implements EmployeeService {
//    private final EmployeeDAO employeeDAO;
//
//    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
//        super(employeeDAO);
//        this.employeeDAO = employeeDAO;
//    }

    @Override
    public List<Employee> findAll(String searchTerm) {
        return employeeDAO.findAll(searchTerm);
    }

    @Override
    public List<Employee> filter(String post, String department) {
        List<Employee> employees = employeeDAO.getAll();
        if(post != null) {
            employees = employees.stream().filter(e -> e.getPost().contains(post)).toList();
        }

        if(department != null) {
            employees = employees.stream().filter(e -> e.getDepartment().getName().equals(department)).toList();
        }
        return employees;
    }
}

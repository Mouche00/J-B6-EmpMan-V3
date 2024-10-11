package services.implementations;

import daos.interfaces.EmployeeDAO;
import models.Employee;
import services.interfaces.EmployeeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named("employeeServiceImpl")
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String> implements EmployeeService {
    @Inject
    private EmployeeDAO employeeDAO;

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

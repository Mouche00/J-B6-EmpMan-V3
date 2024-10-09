package services.implementations;

import DAOs.interfaces.DepartmentDAO;
import DAOs.interfaces.EmployeeDAO;
import DAOs.interfaces.FetchDAO;
import models.Department;
import services.interfaces.DepartmentService;
import services.interfaces.FetchService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDAO departmentDAO;
    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public List<Department> getAll() {
        return departmentDAO.getAll();
    }

    @Override
    public void save(Department department) {
        departmentDAO.save(department);
    }
}

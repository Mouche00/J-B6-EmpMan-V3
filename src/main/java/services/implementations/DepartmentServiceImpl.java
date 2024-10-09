package services.implementations;

import daos.interfaces.DepartmentDAO;
import models.Department;
import services.interfaces.DepartmentService;

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

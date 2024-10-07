package services.implementations;

import DAOs.interfaces.EmployeeDAO;
import DAOs.interfaces.FetchDAO;
import models.Department;
import services.interfaces.FetchService;

import java.util.List;

public class DepartmentServiceImpl implements FetchService<Department> {
    private final FetchDAO<Department> departmentDAO;
    public DepartmentServiceImpl(FetchDAO<Department> departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public List<Department> getAll() {
        return departmentDAO.getAll();
    }
}

package services.implementations;

import daos.interfaces.DepartmentDAO;
import models.Department;
import services.interfaces.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl extends GenericServiceImpl<Department, String> implements DepartmentService {
    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        super(departmentDAO);
        this.genericDAO = departmentDAO;
    }
}

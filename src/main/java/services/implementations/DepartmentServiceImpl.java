package services.implementations;

import models.Department;
import services.interfaces.DepartmentService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import java.util.List;

public class DepartmentServiceImpl extends GenericServiceImpl<Department, String> implements DepartmentService {
}

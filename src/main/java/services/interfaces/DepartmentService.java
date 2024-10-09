package services.interfaces;

import models.Department;

public interface DepartmentService extends FetchService<Department> {
    void save(Department department);
}

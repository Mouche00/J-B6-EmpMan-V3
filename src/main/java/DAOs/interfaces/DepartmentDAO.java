package DAOs.interfaces;

import models.Department;

public interface DepartmentDAO extends FetchDAO<Department> {
    void save(Department department);
}

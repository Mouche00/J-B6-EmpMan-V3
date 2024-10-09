package daos.implementations;

import daos.interfaces.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.JPAUtil;

import java.util.List;

public class DepartmentDAOImpl extends GenericDAOImpl<Department> implements DepartmentDAO {
    protected DepartmentDAOImpl() {
        super(Department.class);
    }
}

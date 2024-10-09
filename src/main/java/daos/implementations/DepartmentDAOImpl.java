package daos.implementations;

import daos.interfaces.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.JPAUtil;

import java.util.List;
import java.util.UUID;

public class DepartmentDAOImpl extends GenericDAOImpl<Department, UUID> implements DepartmentDAO {
    public DepartmentDAOImpl() {
        super(Department.class);
    }
}

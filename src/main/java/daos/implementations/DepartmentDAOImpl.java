package daos.implementations;

import daos.interfaces.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.JPAUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named("departmentDAOImpl")
public class DepartmentDAOImpl extends GenericDAOImpl<Department, UUID> implements DepartmentDAO {
    public DepartmentDAOImpl() {
        super(Department.class);
    }
}

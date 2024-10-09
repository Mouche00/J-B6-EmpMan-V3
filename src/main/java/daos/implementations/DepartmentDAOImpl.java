package daos.implementations;

import daos.interfaces.DepartmentDAO;
import models.Department;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.JPAUtil;

import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {
    @Override
    public List<Department> getAll() {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Department", Department.class).list();
        }
    }

    @Override
    public void save(Department department) {
        try(Session session = JPAUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
        }
    }
}

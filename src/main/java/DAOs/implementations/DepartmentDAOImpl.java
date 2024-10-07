package DAOs.implementations;

import DAOs.interfaces.FetchDAO;
import models.Department;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;

public class DepartmentDAOImpl implements FetchDAO<Department> {
    @Override
    public List<Department> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Department", Department.class).list();
        }
    }
}

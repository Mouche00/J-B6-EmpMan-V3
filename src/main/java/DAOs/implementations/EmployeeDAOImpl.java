package DAOs.implementations;

import DAOs.interfaces.EmployeeDAO;
import models.Department;
import models.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public void save(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Employee> find(String id) {
        Optional<Employee> employee = Optional.empty();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            employee = Optional.ofNullable(
                    session.get(Employee.class, UUID.fromString(id)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public List<Employee> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select e from Employee e join fetch e.department", Employee.class).list();
        }
    }

    public List<Employee> findAll(String searchTerm) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            searchTerm = "%" + searchTerm.toLowerCase() + "%";

            String hql = "select e " +
                        "from Employee e " +
                        "join fetch e.department d " +
                            "where lower(e.name) like :searchTerm " +
                            "or lower(e.email) like :searchTerm " +
                            "or lower(e.post) like :searchTerm " +
                            "or lower(d.name) like :searchTerm";

            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("searchTerm", searchTerm);

            return query.list();
        }
    }
}

package daos.implementations;

import daos.interfaces.EmployeeDAO;
import models.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.JPAUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named("employeeDAOImpl")
public class EmployeeDAOImpl extends GenericDAOImpl<Employee, UUID> implements EmployeeDAO {

    private static final String JPQL_SEARCH_EMPLOYEES =
        "SELECT e " +
        "FROM Employee e " +
        "JOIN e.department d " +
        "WHERE LOWER(e.name) LIKE :searchTerm " +
        "OR LOWER(e.email) LIKE :searchTerm " +
        "OR LOWER(e.post) LIKE :searchTerm " +
        "OR LOWER(d.name) LIKE :searchTerm";

    public EmployeeDAOImpl() {
        super(Employee.class);
    }

    @Override
    public List<Employee> findAll(String searchTerm) {
        String finalSearchTerm = "%" + searchTerm.toLowerCase() + "%";

        return executeInTransaction(entityManager -> {
            TypedQuery<Employee> query = entityManager.createQuery(JPQL_SEARCH_EMPLOYEES, entityClass);
            query.setParameter("searchTerm", searchTerm);
            return query.getResultList();
        });
    }
}

package daos.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.SalaryHistoryDAO;
import jdk.jfr.Name;
import models.ModHistory;
import models.SalaryHistory;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Name("salaryDAO")
public class SalaryHistoryImpl extends GenericDAOImpl<SalaryHistory, UUID> implements SalaryHistoryDAO {
    public SalaryHistoryImpl() {
        super(SalaryHistory.class);
    }

    @Override
    public List<SalaryHistory> findByUserId(UUID userId) {
        String jpql = "SELECT s FROM SalaryHistory s WHERE s.user.id = :id";
        return execute(entityManager -> {
            TypedQuery<SalaryHistory> query = entityManager.createQuery(jpql, entityClass);
            query.setParameter("id", userId);
            return query.getResultList();
        });
    }
}

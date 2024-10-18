package daos.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.ModHistoryDAO;
import models.ModHistory;
import models.SalaryHistory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;


@RequestScoped
@Named("modDAO")
public class ModHistoryImpl extends GenericDAOImpl<ModHistory, UUID> implements ModHistoryDAO {
    public ModHistoryImpl() {
        super(ModHistory.class);
    }

    @Override
    public List<ModHistory> findByUserId(UUID userId) {
        String jpql = "SELECT s FROM ModHistory s WHERE s.user.id = :id";
        return execute(entityManager -> {
            TypedQuery<ModHistory> query = entityManager.createQuery(jpql, entityClass);
            query.setParameter("id", userId);
            return query.getResultList();
        });
    }
}

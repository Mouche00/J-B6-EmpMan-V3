package daos.implementations;

import daos.interfaces.ApplicationDAO;
import models.Application;
import models.User;
import utils.JPAUtil;

import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.UUID;

public class ApplicationDAOImpl extends GenericDAOImpl<Application, UUID> implements ApplicationDAO {
    public ApplicationDAOImpl() {
        super(Application.class);
    }

    public Optional<Application> findByForeignKeys(UUID userId, UUID jobOfferId){
        String jpql = "SELECT a FROM Application a WHERE a.applicant.id == :userId AND a.jobOffer.id = :jobOfferId";

        return Optional.ofNullable(executeInTransaction(entityManager -> {
            TypedQuery<Application> query = entityManager.createQuery(jpql, Application.class);
            query.setParameter("userId", userId);
            query.setParameter("jobOfferId", jobOfferId);
            return query.getSingleResult();
        }));
    }
}

package daos.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class GenericDAO<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
    private final Class<T> entityClass;

    protected GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public boolean save(T entity) {
        return executeInTransaction(entityManager -> {
            entityManager.persist(entity);
            return true;
        });
    }

    public boolean update(T entity) {
        return executeInTransaction(entityManager -> {
            entityManager.merge(entity);
            return true;
        });
    }

    public boolean delete(T entity) {
        return executeInTransaction(entityManager -> {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            return true;
        });
    }

    public Optional<T> find(UUID id) {
        return executeInTransaction(entityManager -> Optional.ofNullable(entityManager.find(entityClass, id)));
    }

    public List<T> getAll() {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList());
    }

    protected <R> R executeInTransaction(EntityManagerFunction<R> function) {
        EntityManager entityManager = JPAUtil.getEntityManager(); // Use the JPAUtil to get EntityManager
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            R result = function.apply(entityManager);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Transaction failed", e);
            throw new DAOException("Operation failed: " + e.getMessage(), e);
        } finally {
            entityManager.close(); // Always close the EntityManager
        }
    }

    @FunctionalInterface
    protected interface EntityManagerFunction<R> {
        R apply(EntityManager entityManager);
    }
}

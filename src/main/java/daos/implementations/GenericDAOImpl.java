package daos.implementations;

import daos.interfaces.GenericDAO;
import exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;


public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(GenericDAOImpl.class);
    protected Class<T> entityClass;

    protected GenericDAOImpl (Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public boolean merge(T entity) {
        return executeInTransaction(entityManager -> {
            entityManager.merge(entity);
            return true;
        });
    }

    public boolean save(T entity) {
        return merge(entity);
    }

    public boolean update(T entity) {
        return merge(entity);
    }

    public boolean delete(T entity) {
        return executeInTransaction(entityManager -> {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
            return true;
        });
    }

    public Optional<T> find(ID id) {
        return execute(entityManager -> Optional.ofNullable(entityManager.find(entityClass, id)));
    }

    public List<T> getAll() {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList());
    }

    protected <R> R executeInTransaction(EntityManagerFunction<R> function) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
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
            throw new DAOException(e);
        } finally {
            entityManager.close();
        }
    }

    protected <R> R execute(EntityManagerFunction<R> function) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            return function.apply(entityManager);
        } catch (Exception e) {
            logger.error("Operation failed", e);
            throw new DAOException(e);
        }
    }

    @FunctionalInterface
    protected interface EntityManagerFunction<R> {
        R apply(EntityManager entityManager);
    }
}

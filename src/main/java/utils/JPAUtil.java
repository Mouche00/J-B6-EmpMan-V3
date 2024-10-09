package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final Logger logger = LoggerFactory.getLogger(JPAUtil.class);

    // Private constructor to prevent instantiation
    private JPAUtil() {}

    // Holder class for lazy initialization
    private static class EntityManagerFactoryHolder {
        private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = buildEntityManagerFactory();
    }

    // Build the EntityManagerFactory from persistence.xml configuration
    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("main-unit");
        } catch (Throwable ex) {
            // Log the exception with proper logging framework
            logger.error("Initial EntityManagerFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Thread-safe access to the EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return EntityManagerFactoryHolder.ENTITY_MANAGER_FACTORY;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    // Properly close EntityManagerFactory when the application is shutting down
    public static void close() {
        try {
            if (EntityManagerFactoryHolder.ENTITY_MANAGER_FACTORY != null &&
                    EntityManagerFactoryHolder.ENTITY_MANAGER_FACTORY.isOpen()) {
                EntityManagerFactoryHolder.ENTITY_MANAGER_FACTORY.close();
            }
        } catch (Exception e) {
            logger.error("Error while closing EntityManagerFactory", e);
        }
    }
}

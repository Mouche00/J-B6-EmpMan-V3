package daos.implementations;

import daos.interfaces.UserDAO;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named("userDAOImpl")
public class UserDAOImpl extends GenericDAOImpl<User, UUID> implements UserDAO {

    private static final String JPQL_SEARCH_USERS =
        "SELECT e " +
        "FROM User e " +
        "WHERE LOWER(e.name) LIKE :searchTerm " +
        "OR LOWER(e.email) LIKE :searchTerm " +
        "OR LOWER(e.post) LIKE :searchTerm ";

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public List<User> findAll(String searchTerm) {
        String finalSearchTerm = "%" + searchTerm.toLowerCase() + "%";

        return executeInTransaction(entityManager -> {
            TypedQuery<User> query = entityManager.createQuery(JPQL_SEARCH_USERS, entityClass);
            query.setParameter("searchTerm", finalSearchTerm);
            return query.getResultList();
        });
    }
}

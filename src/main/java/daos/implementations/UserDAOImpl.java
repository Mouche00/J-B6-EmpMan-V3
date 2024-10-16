package daos.implementations;

import daos.interfaces.UserDAO;
import models.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@Named("userDAOImpl")
public class UserDAOImpl extends GenericDAOImpl<User, UUID> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public List<User> findAll(String searchTerm) {
        String jpql =
                "SELECT e " +
                        "FROM User e " +
                        "WHERE LOWER(e.name) LIKE :searchTerm " +
                        "OR LOWER(e.email) LIKE :searchTerm";
        String finalSearchTerm = "%" + searchTerm.toLowerCase() + "%";

        return execute(entityManager -> {
            TypedQuery<User> query = entityManager.createQuery(jpql, entityClass);
            query.setParameter("searchTerm", finalSearchTerm);
            return query.getResultList();
        });
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";

        return Optional.ofNullable(execute(entityManager -> {
            TypedQuery<User> query = entityManager.createQuery(jpql, entityClass);
            query.setParameter("email", email);
            return query.getSingleResult();
        }));
    }
}

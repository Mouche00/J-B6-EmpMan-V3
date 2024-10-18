package daos.interfaces;

import models.Employee;
import models.SalaryHistory;
import models.User;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO extends GenericDAO<User, UUID> {
    List<User> findAll(String searchTerm);
    Optional<User> findByEmail(String email);
}

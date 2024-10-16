package daos.interfaces;

import models.Employee;
import models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO extends GenericDAO<User, UUID> {
    List<User> findAll(String searchTerm);
    Optional<User> findByEmail(String email);
}

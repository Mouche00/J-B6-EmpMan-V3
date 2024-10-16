package services.interfaces;

import models.Employee;
import models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends GenericService<User, String> {
    List<User> findAll(String searchTerm);
    Map<LocalDate, Double> getSalaryHistory(String id);
    Optional<User> findByEmail(String email);
}

package services.interfaces;

import models.Employee;
import models.User;

import java.util.List;

public interface UserService extends GenericService<User, String> {
    List<User> findAll(String searchTerm);
}

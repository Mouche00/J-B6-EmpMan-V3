package services.implementations;

import daos.interfaces.EmployeeDAO;
import daos.interfaces.UserDAO;
import events.LeaveEvent;
import models.Employee;
import models.User;
import services.interfaces.EmployeeService;
import services.interfaces.UserService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named("userServiceImpl")
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {
    @Inject
    private UserDAO userDAO;

    @Override
    public List<User> findAll(String searchTerm) {
        return userDAO.findAll(searchTerm);
    }

    public void handleLeaveEvent(@Observes LeaveEvent leaveEvent) {
        User user = leaveEvent.getUser();

        if(user != null) {
            int newBalance = Math.toIntExact(user.getLeaveBalance() - leaveEvent.getDays());
            if(newBalance > 0){
                user.setLeaveBalance(newBalance);
                update(user);
            }
        }
    }
}

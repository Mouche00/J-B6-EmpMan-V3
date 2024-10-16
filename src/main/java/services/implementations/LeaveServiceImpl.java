package services.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.LeaveDAO;
import events.LeaveEvent;
import models.Leave;
import models.User;
import services.interfaces.LeaveService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RequestScoped
@Named("leaveServiceImpl")
public class LeaveServiceImpl extends GenericServiceImpl<Leave, String> implements LeaveService {

    @Inject
    private Event<LeaveEvent> leaveEvent;

    @Override
    public boolean save(Leave leave) {
        long days = ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate());
        User user = leave.getUser();

        if(days <= user.getLeaveBalance()) {
            return genericDAO.save(leave);
        }

        return false;
    }

    public boolean validate(String id) {
        Optional<Leave> leave = find(id);

        if(leave.isPresent()) {
            long days = ChronoUnit.DAYS.between(leave.get().getStartDate(), leave.get().getEndDate());
            User user = leave.get().getUser();

            if(days <= user.getLeaveBalance()) {
                leave.get().setValidatedAt(LocalDate.now());
                if(genericDAO.update(leave.get())) {
                    leaveEvent.fire(new LeaveEvent(days, user));
                    return true;
                }
            }
        }

        return false;
    }
}

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
import java.time.temporal.ChronoUnit;

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
            if(genericDAO.save(leave)) {
                leaveEvent.fire(new LeaveEvent(days, user));
                return true;
            }
        }

        return false;
    }
}

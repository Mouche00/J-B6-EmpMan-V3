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
        if (isLeaveWithinBalance(leave)) {
            return genericDAO.save(leave);
        }
        return false;
    }

    @Override
    public boolean validate(String id) {
        Optional<Leave> leaveOptional = find(id);

        if (leaveOptional.isEmpty()) {
            return false;
        }

        Leave leave = leaveOptional.get();
        if (!isLeaveWithinBalance(leave)) {
            return false;
        }

        leave.setValidatedAt(LocalDate.now());

        if (genericDAO.update(leave)) {
            leaveEvent.fire(new LeaveEvent(calculateLeaveDays(leave), leave.getUser()));
            return true;
        }

        return false;
    }

    private long calculateLeaveDays(Leave leave) {
        return ChronoUnit.DAYS.between(leave.getStartDate(), leave.getEndDate());
    }

    private boolean isLeaveWithinBalance(Leave leave) {
        long days = calculateLeaveDays(leave);
        User user = leave.getUser();
        return days <= user.getLeaveBalance();
    }
}

package daos.implementations;

import daos.interfaces.LeaveDAO;
import models.Leave;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;

@RequestScoped
@Named("leaveDAOImpl")
public class LeaveDAOImpl extends GenericDAOImpl<Leave, UUID> implements LeaveDAO {
    public LeaveDAOImpl() {
        super(Leave.class);
    }
}


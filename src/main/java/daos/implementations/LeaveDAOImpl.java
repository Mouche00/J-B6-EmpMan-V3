package daos.implementations;

import daos.interfaces.LeaveDAO;
import models.Leave;

public class LeaveDAOImpl extends GenericDAOImpl<Leave> implements LeaveDAO {
    protected LeaveDAOImpl() {
        super(Leave.class);
    }
}

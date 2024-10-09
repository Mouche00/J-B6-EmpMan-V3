package daos.implementations;

import daos.interfaces.LeaveDAO;
import models.Leave;

import java.util.UUID;

public class LeaveDAOImpl extends GenericDAOImpl<Leave, UUID> implements LeaveDAO {
    public LeaveDAOImpl() {
        super(Leave.class);
    }
}

package daos.implementations;

import daos.interfaces.AdminDAO;
import daos.interfaces.LeaveDAO;
import models.Admin;
import models.Leave;

import java.util.UUID;

public class AdminDAOImpl extends GenericDAOImpl<Admin, UUID> implements AdminDAO {
    public AdminDAOImpl() {
        super(Admin.class);
    }
}

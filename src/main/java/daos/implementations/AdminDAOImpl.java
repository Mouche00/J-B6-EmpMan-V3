package daos.implementations;

import daos.interfaces.AdminDAO;
import daos.interfaces.LeaveDAO;
import models.Admin;
import models.Leave;

public class AdminDAOImpl extends GenericDAOImpl<Admin> implements AdminDAO {
    protected AdminDAOImpl() {
        super(Admin.class);
    }
}

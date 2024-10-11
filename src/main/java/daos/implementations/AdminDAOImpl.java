package daos.implementations;

import daos.interfaces.AdminDAO;
import models.Admin;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.UUID;


@RequestScoped
@Named("adminDAOImpl")
public class AdminDAOImpl extends GenericDAOImpl<Admin, UUID> implements AdminDAO {
    public AdminDAOImpl() {
        super(Admin.class);
    }
}

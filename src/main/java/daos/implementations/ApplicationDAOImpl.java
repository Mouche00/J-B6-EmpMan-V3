package daos.implementations;

import daos.interfaces.ApplicationDAO;
import models.Application;

import java.util.UUID;

public class ApplicationDAOImpl extends GenericDAOImpl<Application, UUID> implements ApplicationDAO {
    protected ApplicationDAOImpl() {
        super(Application.class);
    }
}

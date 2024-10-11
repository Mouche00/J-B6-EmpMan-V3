package daos.implementations;

import daos.interfaces.RecruiterDAO;
import models.Recruiter;

import java.util.UUID;

public class RecruiterDAOImpl extends GenericDAOImpl<Recruiter, UUID> implements RecruiterDAO {
    protected RecruiterDAOImpl() {
        super(Recruiter.class);
    }
}

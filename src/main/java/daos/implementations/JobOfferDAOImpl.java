package daos.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.JobOfferDAO;
import models.JobOffer;

import java.util.UUID;

public class JobOfferDAOImpl extends GenericDAOImpl<JobOffer, UUID> implements JobOfferDAO {
    protected JobOfferDAOImpl() {
        super(JobOffer.class);
    }
}

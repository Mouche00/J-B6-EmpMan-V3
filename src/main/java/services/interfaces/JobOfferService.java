package services.interfaces;

import models.JobOffer;

public interface JobOfferService extends GenericService<JobOffer, String> {
    void checkDeadlines();
}

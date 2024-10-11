package services.implementations;

import models.JobOffer;
import models.enums.JobOfferStatus;
import services.interfaces.JobOfferService;

import java.time.LocalDate;
import java.util.List;

public class JobOfferServiceImpl extends GenericServiceImpl<JobOffer, String> implements JobOfferService {
    @Override
    public void checkDeadlines() {
        List<JobOffer> jobOffers = genericDAO.getAll();

        jobOffers.stream()
                .filter(jobOffer -> jobOffer.getDeadline().isAfter(LocalDate.now()) &&
                        jobOffer.getStatus().equals(JobOfferStatus.OPEN))
                .forEach(jobOffer -> {
                    jobOffer.setStatus(JobOfferStatus.EXPIRED);
                    genericDAO.update(jobOffer);
                });

    }
}

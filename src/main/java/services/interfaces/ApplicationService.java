package services.interfaces;

import models.Application;
import models.BaseUser;
import models.JobOffer;

public interface ApplicationService extends GenericService<Application, String> {
    boolean checkIfExists(BaseUser user, JobOffer jobOffer);
}

package services.implementations;

import daos.interfaces.ApplicationDAO;
import models.Application;
import models.BaseUser;
import models.JobOffer;
import services.interfaces.ApplicationService;

import javax.inject.Inject;

public class ApplicationServiceImpl extends GenericServiceImpl<Application, String> implements ApplicationService {

    @Inject
    private ApplicationDAO applicationDAO;

    @Override
    public boolean save(Application application) {
        BaseUser user = application.getApplicant();
        JobOffer jobOffer = application.getJobOffer();
        if(checkIfExists(user, jobOffer)) {
            return genericDAO.save(application);
        }
        return false;
    }

    @Override
    public boolean checkIfExists(BaseUser user, JobOffer jobOffer) {
        return applicationDAO.findByForeignKeys(user.getId(), jobOffer.getId()).isPresent();
    }
}

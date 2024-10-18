package services.implementations;

import daos.interfaces.GenericDAO;
import models.JobOffer;
import models.enums.JobOfferStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import services.interfaces.JobOfferService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobOfferServiceImplTest {

    @Mock
    private GenericDAO<JobOffer, String> genericDAO;

    @InjectMocks
    private JobOfferServiceImpl jobOfferService;

    @Test
    void testCheckDeadlines_updatesExpiredJobOffers() {
        // Arrange
        JobOffer offer1 = new JobOffer();
        offer1.setStatus(JobOfferStatus.OPEN);
        offer1.setDeadline(LocalDate.now().minusDays(1)); // Already past deadline

        JobOffer offer2 = new JobOffer();
        offer2.setStatus(JobOfferStatus.OPEN);
        offer2.setDeadline(LocalDate.now().plusDays(1)); // Not past deadline

        JobOffer offer3 = new JobOffer();
        offer3.setStatus(JobOfferStatus.EXPIRED); // Already expired
        offer3.setDeadline(LocalDate.now().minusDays(2));

        List<JobOffer> jobOffers = Arrays.asList(offer1, offer2, offer3);

        when(genericDAO.getAll()).thenReturn(jobOffers); // Mock the DAO to return the job offers list

        // Act
        jobOfferService.checkDeadlines();

        // Assert
        // Verify that the status of offer1 has been updated to EXPIRED
        assert offer1.getStatus() == JobOfferStatus.EXPIRED;

        // Verify that the update method was called only once for offer1 (expired job offer)
        verify(genericDAO, times(1)).update(offer1);

        // Verify that offer2 and offer3 were not updated
        verify(genericDAO, never()).update(offer2);
        verify(genericDAO, never()).update(offer3);
    }

    @Test
    void testCheckDeadlines_noUpdatesIfNoOpenExpiredJobOffers() {
        // Arrange
        JobOffer offer1 = new JobOffer();
        offer1.setStatus(JobOfferStatus.EXPIRED);
        offer1.setDeadline(LocalDate.now().minusDays(2)); // Already expired

        JobOffer offer2 = new JobOffer();
        offer2.setStatus(JobOfferStatus.CLOSED);
        offer2.setDeadline(LocalDate.now().minusDays(1)); // Already closed, past deadline

        List<JobOffer> jobOffers = Arrays.asList(offer1, offer2);

        when(genericDAO.getAll()).thenReturn(jobOffers); // Mock the DAO to return the job offers list

        // Act
        jobOfferService.checkDeadlines();

        // Assert
        // Verify that no updates were made because no open jobs are past the deadline
        verify(genericDAO, never()).update(any(JobOffer.class));
    }
}
//package services.implementations;
//
//import daos.interfaces.ApplicationDAO;
//import daos.interfaces.GenericDAO;
//import models.Application;
//import models.BaseUser;
//import models.JobOffer;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ApplicationServiceImplTest {
//
//    @Mock
//    private ApplicationDAO applicationDAO;
//
//    @Mock
//    private GenericDAO<Application, String> genericDAO;
//
//    @InjectMocks
//    private ApplicationServiceImpl applicationService;
//
//    private BaseUser user;
//    private JobOffer jobOffer;
//    private Application application;
//
//    @BeforeEach
//    void setUp() {
//
//        user = new BaseUser();
//        user.setId(UUID.randomUUID());
//
//        jobOffer = new JobOffer();
//        jobOffer.setId(UUID.randomUUID());
//
//        application = new Application();
//        application.setApplicant(user);
//        application.setJobOffer(jobOffer);
//    }
//
//    @Test
//    void testSave_applicationExists_shouldReturnTrue() {
//        // Arrange
//        when(applicationDAO.findByForeignKeys(user.getId(), jobOffer.getId())).thenReturn(Optional.of(application));
//        when(genericDAO.save(application)).thenReturn(true);
//
//        // Act
//        boolean result = applicationService.save(application);
//
//        // Assert
//        assertTrue(result); // Expect the application to be saved successfully
//        verify(applicationDAO, times(1)).findByForeignKeys(user.getId(), jobOffer.getId());
//        verify(genericDAO, times(1)).save(application);
//    }
//
//    @Test
//    void testSave_applicationDoesNotExist_shouldReturnFalse() {
//        // Arrange
//        when(applicationDAO.findByForeignKeys(user.getId(), jobOffer.getId())).thenReturn(Optional.empty());
//
//        // Act
//        boolean result = applicationService.save(application);
//
//        // Assert
//        assertFalse(result); // Expect save to fail since the application does not exist
//        verify(applicationDAO, times(1)).findByForeignKeys(user.getId(), jobOffer.getId());
//        verify(genericDAO, never()).save(application); // Save should not be called if application doesn't exist
//    }
//
//    @Test
//    void testCheckIfExists_applicationExists_shouldReturnTrue() {
//        // Arrange
//        when(applicationDAO.findByForeignKeys(user.getId(), jobOffer.getId())).thenReturn(Optional.of(application));
//
//        // Act
//        boolean result = applicationService.checkIfExists(user, jobOffer);
//
//        // Assert
//        assertTrue(result); // The application exists
//        verify(applicationDAO, times(1)).findByForeignKeys(user.getId(), jobOffer.getId());
//    }
//
//    @Test
//    void testCheckIfExists_applicationDoesNotExist_shouldReturnFalse() {
//        // Arrange
//        when(applicationDAO.findByForeignKeys(user.getId(), jobOffer.getId())).thenReturn(Optional.empty());
//
//        // Act
//        boolean result = applicationService.checkIfExists(user, jobOffer);
//
//        // Assert
//        assertFalse(result); // The application does not exist
//        verify(applicationDAO, times(1)).findByForeignKeys(user.getId(), jobOffer.getId());
//    }
//}
package services.implementations;

import daos.interfaces.GenericDAO;
import events.LeaveEvent;
import models.Employee;
import models.Leave;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.enterprise.event.Event;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LeaveServiceTest {

    @InjectMocks
    private LeaveServiceImpl leaveService;

    @Mock
    private GenericDAO<Leave, UUID> genericDAO;

    @Mock
    private Event<LeaveEvent> leaveEvent;

    @Test
    void testSave_LeaveWithinBalance_ShouldSaveLeave() {
        // Arrange
        Leave leave = createLeave(5, 10); // Leave for 5 days, balance 10 days
        when(genericDAO.save(leave)).thenReturn(true);

        // Act
        boolean result = leaveService.save(leave);

        // Assert
        assertTrue(result);
        verify(genericDAO, atMostOnce()).save(leave); // Ensure save is called once
    }

    @Test
    void testSave_LeaveExceedsBalance_ShouldNotSaveLeave() {
        // Arrange
        Leave leave = createLeave(15, 10); // Leave for 15 days, balance 10 days

        // Act
        boolean result = leaveService.save(leave);

        // Assert
        assertFalse(result);
        verify(genericDAO, never()).save(leave); // Ensure save is never called
    }

    @Test
    void testValidate_ValidLeave_ShouldUpdateAndFireEvent() {
        // Arrange
        Leave leave = createLeave(5, 10); // Valid leave
        leave.setId(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"));
        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenReturn(Optional.of(leave));
        when(genericDAO.update(leave)).thenReturn(true);

        // Act
        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");

        // Assert
        assertTrue(result);
        assertNotNull(leave.getValidatedAt()); // Ensure leave was validated (date was set)
        verify(genericDAO, times(1)).update(leave); // Ensure update is called once
        verify(leaveEvent, times(1)).fire(any(LeaveEvent.class)); // Ensure event is fired
    }

    @Test
    void testValidate_InvalidLeave_ShouldNotUpdateOrFireEvent() {
        // Arrange
        Leave leave = createLeave(15, 10); // Invalid leave, exceeds balance
        leave.setId(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"));
        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenReturn(Optional.of(leave));

        // Act
        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");

        // Assert
        assertFalse(result);
        verify(genericDAO, never()).update(leave); // Ensure update is not called
        verify(leaveEvent, never()).fire(any(LeaveEvent.class)); // Ensure event is not fired
    }

    @Test
    void testValidate_LeaveNotFound_ShouldReturnFalse() {
        // Arrange
        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenReturn(Optional.empty());

        // Act
        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");

        // Assert
        assertFalse(result);
        verify(genericDAO, never()).update(any(Leave.class)); // No update call
        verify(leaveEvent, never()).fire(any(LeaveEvent.class)); // No event firing
    }

//    @Test
//    void testSave_DAOThrowsException_ShouldReturnFalse() {
//        // Arrange
//        Leave leave = createLeave(5, 10); // Valid leave
//        when(genericDAO.save(leave)).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        boolean result = leaveService.save(leave);
//
//        // Assert
//        assertFalse(result);
//        verify(genericDAO, times(1)).save(leave); // Ensure save is attempted
//    }
//
//    @Test
//    void testValidate_DAOThrowsException_ShouldReturnFalse() {
//        // Arrange
//        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenThrow(new RuntimeException("Database error"));
//
//        // Act
//        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");
//
//        // Assert
//        assertFalse(result);
//        verify(genericDAO, never()).update(any(Leave.class)); // No update call
//        verify(leaveEvent, never()).fire(any(LeaveEvent.class)); // No event firing
//    }


    private Leave createLeave(long leaveDays, int userLeaveBalance) {
        Leave leave = new Leave();
        leave.setStartDate(LocalDate.now().minusDays(leaveDays));
        leave.setEndDate(LocalDate.now());

        Employee employee = new Employee();
        employee.setLeaveBalance(userLeaveBalance);
        leave.setUser(employee);

        return leave;
    }
}

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
        Leave leave = createLeave(5, 10);
        when(genericDAO.save(leave)).thenReturn(true);

        boolean result = leaveService.save(leave);

        assertTrue(result);
        verify(genericDAO, atMostOnce()).save(leave);
    }

    @Test
    void testSave_LeaveExceedsBalance_ShouldNotSaveLeave() {
        Leave leave = createLeave(15, 10);

        boolean result = leaveService.save(leave);

        assertFalse(result);
        verify(genericDAO, never()).save(leave);
    }

    @Test
    void testValidate_ValidLeave_ShouldUpdateAndFireEvent() {
        Leave leave = createLeave(5, 10); // Valid leave
        leave.setId(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"));
        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenReturn(Optional.of(leave));
        when(genericDAO.update(leave)).thenReturn(true);

        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");

        assertTrue(result);
        assertNotNull(leave.getValidatedAt());
        verify(genericDAO, times(1)).update(leave);
        verify(leaveEvent, times(1)).fire(any(LeaveEvent.class));
    }

    @Test
    void testValidate_InvalidLeave_ShouldNotUpdateOrFireEvent() {
        Leave leave = createLeave(15, 10);
        leave.setId(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"));
        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenReturn(Optional.of(leave));

        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");

        assertFalse(result);
        verify(genericDAO, never()).update(leave);
        verify(leaveEvent, never()).fire(any(LeaveEvent.class));
    }

    @Test
    void testValidate_LeaveNotFound_ShouldReturnFalse() {
        when(genericDAO.find(UUID.fromString("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276"))).thenReturn(Optional.empty());

        boolean result = leaveService.validate("fe34d0c6-5377-4ab7-bd4b-a0b94ce44276");

        assertFalse(result);
        verify(genericDAO, never()).update(any(Leave.class));
        verify(leaveEvent, never()).fire(any(LeaveEvent.class));
    }


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

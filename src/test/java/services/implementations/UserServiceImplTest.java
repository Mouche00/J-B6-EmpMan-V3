package services.implementations;

import daos.interfaces.GenericDAO;
import daos.interfaces.ModHistoryDAO;
import daos.interfaces.SalaryHistoryDAO;
import daos.interfaces.UserDAO;
import events.LeaveEvent;
import models.Employee;
import models.ModHistory;
import models.SalaryHistory;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.JPAUtil;
import utils.JSONUtil;
import utils.ObjectMapperFactory;
import utils.ReflectionUtil;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private ReflectionUtil reflectionUtil;

    @Mock
    private GenericDAO<User, UUID> genericDAO;

    @Mock
    private UserDAO userDAO;

    @Mock
    private ModHistoryDAO modHistoryDAO;

    @Mock
    private SalaryHistoryDAO salaryHistoryDAO;

    @Mock
    private JSONUtil jsonUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private Employee mockUser;
    private Employee updatedUser;

    @BeforeEach
    void setUp() {
        mockUser = new Employee();
        mockUser.setId(UUID.randomUUID());
        mockUser.setSalary(5000.0);
        mockUser.setChildren(2);
        mockUser.setLeaveBalance(20);

        updatedUser = new Employee();
        updatedUser.setId(mockUser.getId());
        updatedUser.setSalary(5500.0);
        updatedUser.setChildren(3);
        updatedUser.setLeaveBalance(18);
    }

    @Test
    void testFind() {
        when(genericDAO.find(any(UUID.class))).thenReturn(Optional.of(mockUser));

        Optional<User> result = userService.find(mockUser.getId().toString());

        assertTrue(result.isPresent());
        assertEquals(mockUser.getId(), result.get().getId());
        verify(genericDAO, times(1)).find(any(UUID.class));
    }

    @Test
    void testUpdateWithSalaryAndChildrenModified() {
        Map<String, Object> modifications = new HashMap<>();
        modifications.put("salary", 5500.0);
        modifications.put("children", 3);

        when(genericDAO.find(any(UUID.class))).thenReturn(Optional.of(mockUser));
        when(genericDAO.update(any())).thenReturn(true);
        when(reflectionUtil.trackModifiedAttributes(any(), any())).thenReturn(modifications);
        when(jsonUtil.serializeMap(any())).thenReturn("{ \"salary\": 5500.0, \"children\": 3 }");

        boolean result = userService.update(updatedUser);

        assertTrue(result);
        verify(salaryHistoryDAO, times(1)).save(any(SalaryHistory.class));
        verify(modHistoryDAO, times(0)).save(any(ModHistory.class));
        verify(genericDAO, times(1)).update(any(User.class));
    }

    @Test
    void testUpdateWithNoModifications() {
        when(genericDAO.find(any(UUID.class))).thenReturn(Optional.of(mockUser));
        when(genericDAO.update(any())).thenReturn(true);
        when(reflectionUtil.trackModifiedAttributes(any(), any())).thenReturn(Collections.emptyMap());

        boolean result = userService.update(mockUser);

        assertTrue(result);
        verify(salaryHistoryDAO, times(0)).save(any(SalaryHistory.class));
        verify(modHistoryDAO, times(0)).save(any(ModHistory.class));
        verify(genericDAO, times(1)).update(any(User.class));
    }

    @Test
    void testFindAll() {
        List<User> users = Arrays.asList(mockUser, updatedUser);
        when(userDAO.findAll(anyString())).thenReturn(users);

        List<User> result = userService.findAll("test");

        assertEquals(2, result.size());
        verify(userDAO, times(1)).findAll(anyString());
    }

    @Test
    void testGetSalaryHistory() {
        SalaryHistory history = new SalaryHistory("{ \"salary\": 5500.0, \"children\": 3 }", LocalDate.of(2022, 10, 18), mockUser);
        List<SalaryHistory> salaryHistories = Collections.singletonList(history);

        when(genericDAO.find(any(UUID.class))).thenReturn(Optional.of(mockUser));
        when(salaryHistoryDAO.findByUserId(any(UUID.class))).thenReturn(salaryHistories);
        when(jsonUtil.convertJsonToMap(anyString())).thenReturn(Optional.of(Map.of("salary", 5500.0, "children", 3)));

        Map<LocalDate, Double> salaryHistory = userService.getSalaryHistory(mockUser.getId().toString());

        assertEquals(2, salaryHistory.size());
        assertTrue(salaryHistory.containsKey(LocalDate.of(2022, 10, 18)));
        assertTrue(salaryHistory.containsKey(LocalDate.now()));

    }

    @Test
    void testHandleLeaveEvent() {
        LeaveEvent leaveEvent = new LeaveEvent(5, mockUser);
        when(genericDAO.find(any(UUID.class))).thenReturn(Optional.of(mockUser));

        userService.handleLeaveEvent(leaveEvent);

        verify(genericDAO, times(1)).update(any(User.class));
        assertEquals(15, mockUser.getLeaveBalance());
    }

    @Test
    void testFindByEmail() {
        when(userDAO.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

        Optional<User> foundUser = userService.findByEmail(anyString());

        assertTrue(foundUser.isPresent());
        verify(userDAO, atMostOnce()).findByEmail(anyString());
    }
}

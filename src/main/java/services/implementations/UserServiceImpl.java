package services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import daos.interfaces.EmployeeDAO;
import daos.interfaces.ModHistoryDAO;
import daos.interfaces.SalaryHistoryDAO;
import daos.interfaces.UserDAO;
import events.LeaveEvent;
import models.Employee;
import models.ModHistory;
import models.SalaryHistory;
import models.User;
import services.interfaces.EmployeeService;
import services.interfaces.UserService;
import utils.JPAUtil;
import utils.JSONUtil;
import utils.ObjectMapperFactory;
import utils.ReflectionUtil;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
@Named("userServiceImpl")
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private ModHistoryDAO modHistoryDAO;

    @Inject
    private SalaryHistoryDAO salaryHistoryDAO;

    @Override
    public Optional<User> find(String id) {
        return genericDAO.find(UUID.fromString(id));
    }

    @Override
    public boolean update(User user) {
        Optional<User> existingUser = find(user.getId().toString());

        existingUser.ifPresent(u -> {
            Map<String, Object> mods = ReflectionUtil.trackModifiedAttributes(u, user);
            if (mods.isEmpty() || mods.containsKey("leaveBalance")) return;

            mods.putIfAbsent("salary", u.getSalary());
            mods.putIfAbsent("children", u.getChildren());

            String modsString = JSONUtil.serializeMap(mods);
            if (mods.size() == 2 && mods.containsKey("salary") && mods.containsKey("children")) {
                salaryHistoryDAO.save(new SalaryHistory(modsString, LocalDate.now(), user));
            } else {
                modHistoryDAO.save(new ModHistory(modsString, LocalDate.now(), user));
            }
        });

        return genericDAO.update(user);
    }

    @Override
    public List<User> findAll(String searchTerm) {
        return userDAO.findAll(searchTerm);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public Map<LocalDate, Double> getSalaryHistory(String id) {
        Optional<User> user = find(id);

        EntityManager em = JPAUtil.getEntityManager();
        String jpql = "SELECT s FROM SalaryHistory s WHERE s.user.id = :id";
        List<SalaryHistory> salaryHistory = em.createQuery(jpql, SalaryHistory.class)
                .setParameter("id", UUID.fromString(id))
                .getResultList();


        Map<LocalDate, Double> finalSalaryHistory = salaryHistory.stream()
                .collect(Collectors.toMap(
                        SalaryHistory::getDate,
                        history -> {
                            String modificationsJson = history.getModifications();
                            Optional<Map<String, Object>> modificationsMap = JSONUtil.convertJsonToMap(modificationsJson);
                            return modificationsMap
                                    .map(m -> calculateFinalSalary((double) m.get("salary"), (int) m.get("children")))
                                    .orElse(0.0);
                        }
                ));

        user.ifPresent(u -> finalSalaryHistory.put(LocalDate.now(), calculateFinalSalary(u.getSalary(), u.getChildren())));

        return finalSalaryHistory;
    }

    private double calculateFinalSalary(double salary, int children) {
        final int numChildren = Math.min(children, 6);
        final int firstThreeChildren = Math.min(numChildren, 3);
        final int additionalChildren = Math.max(0, numChildren - 3);

        double bonus = 0;

        if (salary <= 6000) {
            bonus = firstThreeChildren * 300 + additionalChildren * 150;
        } else if (salary >= 8000) {
            bonus = firstThreeChildren * 200 + additionalChildren * 110;
        }

        return salary + bonus;
    }

    public void handleLeaveEvent(@Observes LeaveEvent leaveEvent) {
        User user = leaveEvent.getUser();

        if(user != null) {
            int newBalance = Math.toIntExact(user.getLeaveBalance() - leaveEvent.getDays());
            if(newBalance > 0){
                user.setLeaveBalance(newBalance);
                update(user);
            }
        }
    }
}

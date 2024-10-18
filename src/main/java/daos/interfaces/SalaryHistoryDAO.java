package daos.interfaces;

import models.SalaryHistory;

import java.util.List;
import java.util.UUID;

public interface SalaryHistoryDAO extends GenericDAO<SalaryHistory, UUID>{
    List<SalaryHistory> findByUserId(UUID userId);
}

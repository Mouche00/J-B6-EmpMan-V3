package daos.interfaces;

import models.ModHistory;

import java.util.List;
import java.util.UUID;

public interface ModHistoryDAO extends GenericDAO<ModHistory, UUID>{
    public List<ModHistory> findByUserId(UUID userId);
}

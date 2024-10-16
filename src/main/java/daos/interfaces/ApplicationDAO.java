package daos.interfaces;

import models.Application;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationDAO extends GenericDAO<Application, UUID> {
    public Optional<Application> findByForeignKeys(UUID userId, UUID jobOfferId);
}

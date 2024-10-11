package services.implementations;

import daos.interfaces.GenericDAO;
import services.interfaces.GenericService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {
    @Inject
    protected GenericDAO<T, UUID> genericDAO;

    @Override
    public boolean save(T entity) {
        return genericDAO.save(entity);
    }

    @Override
    public boolean update(T entity) {
        return genericDAO.update(entity);
    }

    @Override
    public boolean delete(ID id) {
        Optional<T> entity = find(id);
        return entity.map(genericDAO::delete).orElse(false);
    }

    @Override
    public Optional<T> find(ID id) {
        return genericDAO.find(UUID.fromString((String) id));
    }

    @Override
    public List<T> getAll() {
        return genericDAO.getAll();
    }
}

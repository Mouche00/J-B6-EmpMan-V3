package services.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericService<T, ID> {

    boolean save(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    Optional<T> find(ID id);
    List<T> getAll();
}

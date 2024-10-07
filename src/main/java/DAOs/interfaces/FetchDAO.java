package DAOs.interfaces;

import java.util.List;

public interface FetchDAO<T> {
    List<T> getAll();
}

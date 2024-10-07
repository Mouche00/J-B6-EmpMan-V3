package services.interfaces;

import java.util.List;

public interface FetchService<T> {
    List<T> getAll();
}

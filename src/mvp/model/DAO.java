package mvp.model;

import java.util.List;

public interface DAO<T> {

    T add(T object);

    T read(T object);

    T readbyId(int id);

    T update(T modifiedObject);

    boolean remove(T object);

    List<T> getAll();

}

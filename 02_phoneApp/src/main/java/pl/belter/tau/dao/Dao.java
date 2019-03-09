package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Long save(Phone o) throws IllegalArgumentException;

    List<T> getAll();

    Optional<T> get(Long id) throws IllegalArgumentException;

    Long update(T o) throws IndexOutOfBoundsException;

    Long delete(T o);
}

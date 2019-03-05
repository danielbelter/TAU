package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void save(Phone o);
    List<T> getAll();
    Optional<T> get(int id);
}

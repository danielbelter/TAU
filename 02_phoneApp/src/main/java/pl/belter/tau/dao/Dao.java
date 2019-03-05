package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.util.List;

public interface Dao<T> {
    void save(Phone o);

    List<T> getAll();
}

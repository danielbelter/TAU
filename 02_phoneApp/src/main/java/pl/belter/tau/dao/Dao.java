package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

public interface Dao<T> {
    void save(Phone o);
}

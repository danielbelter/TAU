package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneInMemoryDao implements Dao<Phone> {
    public List<Phone> phoneDB = new ArrayList<>();

    @Override
    public void save(Phone o) {
        phoneDB.add(o);
    }

    @Override
    public List<Phone> getAll() {
        return phoneDB;
    }

    @Override
    public Optional<Phone> get(int id) throws IllegalArgumentException  {
        if(phoneDB.get(id) == null ){
            throw new IllegalArgumentException("error");
        }
        return Optional.ofNullable(phoneDB.get(id));
    }
}

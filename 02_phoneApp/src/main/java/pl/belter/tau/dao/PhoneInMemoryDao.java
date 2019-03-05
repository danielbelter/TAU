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
    public Optional<Phone> get(int id) throws IllegalArgumentException {
        if (phoneDB.get(id) == null) {
            throw new IllegalArgumentException("error");
        }
        return Optional.ofNullable(phoneDB.get(id));
    }

    @Override
    public void update(Phone o) throws IllegalArgumentException {
        if(o.getId()!=null){
            throw new IllegalArgumentException("ID does not exist");
        }
        Phone p = phoneDB.get(o.getId().intValue()-1);
        p = o;
    }

    @Override
    public Long delete(Phone o) {
        Long id = o.getId();
        phoneDB.remove(o);
        return id;
    }
}


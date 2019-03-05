package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneInMemoryDao implements Dao<Phone> {
    public List<Phone> phoneDB = new ArrayList<>();

    @Override
    public void save(Phone o) {
        phoneDB.add(o);
    }
}

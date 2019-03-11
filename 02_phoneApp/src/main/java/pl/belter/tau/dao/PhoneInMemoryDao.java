package pl.belter.tau.dao;

import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneInMemoryDao implements Dao<Phone> {
    public List<Phone> phoneDB = new ArrayList<>();

    @Override
    public Long save(Phone o) throws IllegalArgumentException {
        if (phoneDB.stream().anyMatch(phone -> phone.getId().equals(o.getId()))) {
            throw new IllegalArgumentException("Phone exist in db");
        }
        phoneDB.add(o);
        return o.getId();
    }

    @Override
    public List<Phone> getAll() {
        return phoneDB;
    }

    @Override
    public Optional<Phone> get(Long id) throws IllegalArgumentException {
        if (!phoneDB.stream().anyMatch(phone -> phone.getId().equals(id))) {
            throw new IllegalArgumentException("error");
        }
        return phoneDB.stream().filter(phone -> phone.getId().equals(id)).findFirst();
    }

    @Override
    public Long update(Phone o) throws IndexOutOfBoundsException {
        if (phoneDB.get(o.getId().intValue()) == null) {
            throw new IllegalArgumentException("Phone does not exist");
        }
        Phone p = phoneDB.get(o.getId().intValue() - 1);
        if (o.getModel() == null) {
            o.setModel(o.getModel());

        } else {
            p.setModel(o.getModel());
        }
        p.setSerialNumber(o.getSerialNumber());
        return o.getId();
    }

    @Override
    public Long delete(Phone o) {
        Long id = o.getId();
        phoneDB.remove(o);
        return id;
    }
}


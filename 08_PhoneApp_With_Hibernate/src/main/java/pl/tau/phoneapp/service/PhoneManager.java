package pl.tau.phoneapp.service;

import java.util.List;

import pl.tau.phoneapp.domain.Owner;
import pl.tau.phoneapp.domain.Phone;

public interface PhoneManager {

    Long addPhonejkk(Phone phone);

    List<Phone> findAllPhone();

    Phone findPhoneById(Long id);

    void deletePhone(Phone phone);

    void updatePhone(Phone phone);

    List<Phone> findPhoneByName(String phoneName);

    Long addOnwer(Owner owner);

    List<Phone> getAllPhonesForOwner(Owner owner);

    Owner findOwnerById(Long id);

    void transferPhoneToAnotherOwner(Phone phone1, Phone phone2, Owner owner1, Owner owner2);

}

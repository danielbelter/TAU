package pl.tau.phoneapp.service;

import java.util.List;

import pl.tau.phoneapp.domain.Phone;

public interface PhoneManager {
	
	Long addPhonejkk(Phone phone);
	List<Phone> findAllPhone();
	Phone findPhoneById(Long id);
	void deletePhone(Phone phone);
	void updatePhone(Phone phone);
	List<Phone> findPhoneByName(String phoneName);

}

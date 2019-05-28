package pl.tau.phoneapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.tau.phoneapp.domain.Owner;
import pl.tau.phoneapp.domain.Phone;

@Ignore // You can turn it on if needed
@RunWith(SpringRunner.class)
//@SpringBootTest
@ComponentScan({"pl.tau.phoneapp"})
@PropertySource("classpath:jdbc.properties")
@ImportResource({"classpath:/beans.xml"})
@Rollback
//@Commit
@Transactional ("txManager")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryManagerTest {

	@Autowired
	PhoneManager libraryManager;

	List<Long> phoneIds;
	private List<Long> ownerIds;

	@Before
	public void setup() {
		phoneIds = new LinkedList<>();
		phoneIds.add(libraryManager.addPhonejkk(new Phone("iPhone", 456)));
		phoneIds.add(libraryManager.addPhonejkk(new Phone("Motorolla", 123)));
		phoneIds.add(libraryManager.addPhonejkk(new Phone("Nokia", 789)));

		Phone phone = libraryManager.findPhoneById(phoneIds.get(0));
		Phone phone1 = libraryManager.findPhoneById(phoneIds.get(1));
		ownerIds = new LinkedList<>();
		ownerIds.add(libraryManager.addOnwer(new Owner("Marek", new LinkedList<Phone>(Arrays.asList(phone)))));
		ownerIds.add(libraryManager.addOnwer(new Owner("Paulina", new LinkedList<Phone>(Arrays.asList(phone1)))));
	}

	@Test
	public void addPhoneTest() {
		assertTrue(phoneIds.size() > 0);
	}

	@Test
	public void getAllPhonesTest() {
		List<Long> foundIds = new LinkedList<>();
		for (Phone phone : libraryManager.findAllPhone()) {
			if (phoneIds.contains(phone.getId())) foundIds.add(phone.getId());
		}
		assertEquals(phoneIds.size(), foundIds.size());
	}


	@Test
	public void deletePhoneTest() {
		int prevSize = libraryManager.findAllPhone().size();
		Phone phone = libraryManager.findPhoneById(phoneIds.get(0));
		assertNotNull(phone);
		libraryManager.deletePhone(phone);
		assertNull(libraryManager.findPhoneById(phoneIds.get(0)));
		assertEquals(prevSize - 1, libraryManager.findAllPhone().size());
	}

	@Test
	public void findPhoneByNameTest() {
		List<Phone> phones = libraryManager.findPhoneByName("Nok");
		assertEquals("Nokia", phones.get(0).getModel());
	}

	@Test()
	public void updatePhoneTest() {
		Phone p = libraryManager.findPhoneById(1L);
		p.setModel("Sony");
		libraryManager.updatePhone(p);
		Assert.assertEquals("Sony", libraryManager.findPhoneById(1L).getModel());
	}

	@Test
	public void findPhonesByOwner() {
		Owner owner = libraryManager.findOwnerById(ownerIds.get(1));
		List<Phone> phones = libraryManager.getAllPhonesForOwner(owner);
		assertEquals(1, phones.size());
	}

	@Test
	public void transferPhoneToAnotherOwner() {
		Phone phone = libraryManager.findPhoneById(phoneIds.get(0));
		Phone phone1 = libraryManager.findPhoneById(phoneIds.get(1));
		Owner owner = libraryManager.findOwnerById(ownerIds.get(0));
		Owner owner1 = libraryManager.findOwnerById(ownerIds.get(1));
		libraryManager.transferPhoneToAnotherOwner(
				phone, phone1, owner, owner1);
		assertEquals("Motorolla", libraryManager.getAllPhonesForOwner(owner).get(0).getModel());
		assertEquals(1, libraryManager.getAllPhonesForOwner(owner1).size());

	}
}

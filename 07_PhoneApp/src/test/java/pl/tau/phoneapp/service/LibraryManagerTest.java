package pl.tau.phoneapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.tau.phoneapp.domain.Phone;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans.xml"})
//@Rollback
@Commit
@Transactional(transactionManager = "txManager")
public class LibraryManagerTest {

    @Autowired
    PhoneManager libraryManager;

    List<Long> phoneIds;

    private Phone addPhoneHelper(String name, Integer serial) {
        Long phoneId;
        Phone phone;
        phone = new Phone();
        phone.setModel(name);
        phone.setSerialNumber(serial);
        phone.setId(null);
        phoneIds.add(phoneId = libraryManager.addPhonejkk(phone));
        assertNotNull(phoneId);
        return phone;
    }


    @Before
    public void setup() {
        phoneIds = new LinkedList<>();
        addPhoneHelper("Motorolla", 456);
        addPhoneHelper("iPhone", 123456);
        Phone phone = addPhoneHelper("Nokia", 99999);
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
        Assert.assertEquals("Sony",libraryManager.findPhoneById(1L).getModel());
    }
}

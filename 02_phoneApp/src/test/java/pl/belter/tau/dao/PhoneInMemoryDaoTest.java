package pl.belter.tau.dao;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
public class PhoneInMemoryDaoTest {
    PhoneInMemoryDao dao;

    @Before
    public void setup() {
        dao = new PhoneInMemoryDao();
        Phone p1 = new Phone(1L, "Nokia", 3310);
        Phone p2 = new Phone(2L, "Sony", 1123);
        Collections.addAll(dao.phoneDB, p1, p2);

    }

    @Test
    public void createDaoObjectTest() {
        Assert.assertNotNull(dao);
    }

    @Test
    public void savePhoneTest() {
        Phone p3 = new Phone(3L, "Motorolla", 515);
        Assert.assertEquals(new Long(3), p3.getId());
    }

    @Test
    public void gettingAllPhonesTest() {
        Assert.assertArrayEquals(dao.phoneDB.toArray(), dao.getAll().toArray());
        Assert.assertEquals(dao.phoneDB.size(), dao.getAll().size());
    }

    @Test
    public void getPhoneById() {
        Optional<Phone> p = dao.get(1);
        Assert.assertThat(p.get().getModel(), is("Sony"));
    }

    @Test
    public void checkDeleteMethod() {
        Phone phone = dao.get(1).get();
        Assert.assertEquals(2, dao.delete(phone).longValue());
        Assert.assertEquals(1, dao.phoneDB.size());
    }

    @Test
    public void checkUpdateMethod() {
        Phone phone = dao.get(1).get();
        phone.setModel("model1");
        phone.setSerialNumber(50);
        Assert.assertEquals("model1", dao.get(1).get().getModel());
        Assert.assertEquals(50, dao.get(1).get().getSerialNumber());


    }
}




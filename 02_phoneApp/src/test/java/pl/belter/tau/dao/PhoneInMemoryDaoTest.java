package pl.belter.tau.dao;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        Assert.assertEquals(3L, dao.save(p3).longValue());
        Assert.assertEquals(dao.phoneDB.size(), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void savingExistTest() {
        Phone p1 = new Phone(1L, "Motorolla", 515);
        dao.save(p1);
    }

    @Test
    public void gettingAllPhonesTest() {
        Assert.assertArrayEquals(dao.phoneDB.toArray(), dao.getAll().toArray());
        Assert.assertEquals(dao.phoneDB.size(), dao.getAll().size());
    }

    @Test
    public void getPhoneById() {
        Phone p = new Phone(3L, "CCC", 456);
        dao.save(p);
        Assert.assertEquals(p, dao.get(3L).get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkGettingTest() {
        dao.get(5L);
    }

    @Test
    public void checkDeleteMethod() {
        Phone phone = dao.get(1L).get();
        Assert.assertEquals(1L, dao.delete(phone).longValue());
        Assert.assertEquals(1L, dao.phoneDB.size());
    }

    @Test
    public void checkUpdateMethod() {
        Phone phone = new Phone();
        phone.setId(1L);
        phone.setModel("model1");
        phone.setSerialNumber(50);
        dao.update(phone);
        Assert.assertEquals("model1", dao.get(1L).get().getModel());
        Assert.assertEquals(50, dao.get(1L).get().getSerialNumber());


    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkUpdateExistMethodTest() {
        Phone p = new Phone(3L, "CCC", 456);
        dao.update(p);
    }
}




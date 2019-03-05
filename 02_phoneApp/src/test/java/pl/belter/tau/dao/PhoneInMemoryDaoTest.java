package pl.belter.tau.dao;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.Collections;

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

}
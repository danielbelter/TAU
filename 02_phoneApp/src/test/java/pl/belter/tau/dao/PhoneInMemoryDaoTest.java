package pl.belter.tau.dao;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PhoneInMemoryDaoTest {
    PhoneInMemoryDao dao;

    @Before
    public void setup() {
        dao = new PhoneInMemoryDao();
    }

    @Test
    public void createDaoObjectTest() {
        Assert.assertNotNull(dao);
    }

}

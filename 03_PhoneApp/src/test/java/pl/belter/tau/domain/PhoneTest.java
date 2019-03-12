package pl.belter.tau.domain;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PhoneTest {
    /*
    @Test
    public void checkPhone() {
        Phone phone = new Phone(1L, "Nokia", 3310);
        Assert.assertNotNull(phone);
    }

*/
    @Test
    public void phoneGetterAndSetterTest() {
        Phone phone = new Phone();
        phone.setId(1L);
        phone.setModel("Sony");
        phone.setSerialNumber(123);
        Assert.assertEquals(new Long(1), phone.getId());
    }

}

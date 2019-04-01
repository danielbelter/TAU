package pl.belter.tau;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.belter.tau.dao.PhoneInMemoryDao;
import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.Collections;

public class UpdatePhoneStep {
    private PhoneInMemoryDao phoneInMemoryDao;
    public Phone phone = new Phone(6L, "Alcatel", 515);

    @Given("^Phone \"([^\"]*)\"$")
    public void phone_p(String model) {
        phoneInMemoryDao = new PhoneInMemoryDao();
        phoneInMemoryDao.phoneDB = new ArrayList<>();
        Collections.addAll(phoneInMemoryDao.phoneDB,
                new Phone(1L, "Nokia", 123),
                new Phone(2L, "Motorolla", 456),
                new Phone(3L, "iPhone", 789),
                new Phone(4L, "Samsung", 911),
                new Phone(5L, "Sony", 112));

        phoneInMemoryDao.phoneDB.add(phone);

    }

    @When("^Phone serial number should be updated to (\\d+)$")
    public void phone_serial_number_should_be_updated(int serialNumber) {
        phoneInMemoryDao.phoneDB.get(5).setSerialNumber(serialNumber);

    }

    @Then("^Phone have a new serial number$")
    public void phone_new_serial() {
        Assert.assertEquals(5151, phoneInMemoryDao.phoneDB.get(5).getSerialNumber());
    }

}

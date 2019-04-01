package pl.belter.tau;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.belter.tau.dao.PhoneInMemoryDao;
import pl.belter.tau.domain.Phone;

import java.util.ArrayList;

public class NewPhone {

    private PhoneInMemoryDao phoneInMemoryDao;
    private int phoneCounter;

    @Given("^Delivery of 1 phone$")
    public void phone_delivery() {
        phoneInMemoryDao = new PhoneInMemoryDao();
        phoneInMemoryDao.phoneDB = new ArrayList<>();
    }

    @When("^Phone has been delivered$")
    public void phone_has_been_delivered() {
        phoneCounter = phoneInMemoryDao.phoneDB.size();
        phoneInMemoryDao.save(new Phone(1L, "Nokia", 123));
    }

    @Then("^Quantity of phones has been increased by (\\d+)$")
    public void quantity_of_phones_has_been_increased_by(int quantityOfAddedCars) {
        Assert.assertEquals(phoneCounter + quantityOfAddedCars, phoneInMemoryDao.phoneDB.size());
    }

}

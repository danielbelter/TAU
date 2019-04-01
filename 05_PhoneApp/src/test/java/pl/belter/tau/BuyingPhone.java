package pl.belter.tau;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.belter.tau.dao.PhoneInMemoryDao;
import pl.belter.tau.domain.Phone;

import java.util.ArrayList;
import java.util.Collections;

public class BuyingPhone {
    private PhoneInMemoryDao phoneInMemoryDao;
    private String selectedModel;
    private Integer selectedSerialNumber;

    @Given("^Customer choses a phone$")
    public void customer_chooses_a_phone() {
        phoneInMemoryDao = new PhoneInMemoryDao();
        phoneInMemoryDao.phoneDB = new ArrayList<>();
        Collections.addAll(phoneInMemoryDao.phoneDB,
                new Phone(1L, "Nokia", 123),
                new Phone(2L, "Motorolla", 456),
                new Phone(3L, "iPhone", 789),
                new Phone(4L, "Samsung", 911),
                new Phone(5L, "Sony", 112)
        );

    }

    @When("^Customer chose model \"([^\"]*)\"$")
    public void customer_chose_model(String model) {
        selectedModel = model;
    }

    @When("^Customer chose serialnumber (\\d+)$")
    public void customer_searching_model_with_a_serialnumber(int int1) {
        selectedSerialNumber = int1;
    }



    @Then("^Phone has benn sold$")
    public void phone_has_benn_sold() {
        Phone choosedPhone = phoneInMemoryDao.getAll().stream().filter(p -> p.getModel().equals(selectedModel) && p.getSerialNumber() == selectedSerialNumber).findFirst().get();
        Assert.assertEquals(choosedPhone, phoneInMemoryDao.get(choosedPhone.getId()).get());
        phoneInMemoryDao.delete(choosedPhone);
        Assert.assertEquals(4, phoneInMemoryDao.phoneDB.size());
    }
}

package pl.belter.tau;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

class CheckPhoneModel {
    static String isItIphone(String today) {
        if (today.equals("iPhone")) {
            return "Yes";
        }
        return "Nope";
    }

}

public class Stepdefs {
    private String modelName;
    private String actualAnswer;


    @Given("^this is \"([^\"]*)\"$")
    public void this_is(String today) {
        this.modelName = today;
    }

    @When("^I ask are you sure I's iPhone$")
    public void I_ask_are_you_sure_iPhone() {
        this.actualAnswer = CheckPhoneModel.isItIphone(modelName);
    }

    @Then("^I should be told \"([^\"]*)\"$")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
    }
}
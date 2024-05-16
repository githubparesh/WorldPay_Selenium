package Steps;

import com.qa.demo.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ParkingCostCalculationStepDefination {
    HomePage homePage = new HomePage();


    @Given("user is on parking cost calculator homepage and select the Parking lot as {string}")
    public void user_is_on_the_parking_cost_calculator_homepage(String lot) throws InterruptedException {
        homePage.openParkingCostCalculatorHomePage();
        homePage.selectParkingLot(lot);
    }

    @When("user enters an entryDate as {string} and entryTime as {string} {string}")
    public void a_customer_parks_the_car(String date, String time, String ampm) {
        homePage.setStartingDateTime(date, time, ampm);
    }

    @Then("user enters an exitDate as {string} and exitTime as {string} {string}")
    public void the_customer_retrieves_the_car(String date, String time, String ampm) {
        homePage.setLeavingDateTime(date, time, ampm);
    }

    @When("the parking cost is calculated as {string}")
    public void the_parking_duration_is_calculated(String totalCost) {
        homePage.calculateCost();
        homePage.testParkingCostCalculation(totalCost);
        homePage.closeBrowser();
    }

}

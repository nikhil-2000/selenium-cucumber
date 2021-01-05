package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

class IsItFriday {
    static String isItFriday(String today) {
        return today.equals("Friday") ? "TGIF" : "Nope";
    }
}

public class StepDefinitions {
    private String today;
    private String actualAnswer;

    private String team;
    private LocalDate date;
    private List<String> teams_on_date;
    private WebDriver driver;

       @Given("Initialize Driver")
       public void init_driver(){
           System.setProperty("webdriver.chrome.driver", "chromedriver_win32/chromedriver.exe");
           driver = new ChromeDriver();
       }

    @Given("today is {string}")
    public void today_is(String today) {
        this.today = today;
    }

    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_Friday_yet() {
        actualAnswer = IsItFriday.isItFriday(today);
    }

    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        assertEquals(expectedAnswer, actualAnswer);
    }


    @Given("team is {string}")
    public void teamIsTeam(String given_team) {
        team = given_team;
    }

    @Given("date is {string}")
    public void dateIsDate(String given_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date = LocalDate.parse(given_date,formatter);
    }

    @When("I navigate to PL Fixtures")
    public void navigate_to_fixtures(){

        driver.get("https://www.premierleague.com/fixtures");
    }

    @Then("I verify I am on PL Fixtures page")
    public void i_verify_i_am_on_pl_fixtures_page() {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.premierleague.com/fixtures");

    }

    @When("I load the fixtures for {string}")
    public void i_load_the_fixtures_for(String string) {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        String converted_date = date.format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy"));

        String xpath = String.format("//div[@data-competition-matches-list='%s']//span[@class='shortname']",converted_date);
        List<WebElement> fixtures_for_date = driver.findElements(By.xpath(xpath));
        teams_on_date = fixtures_for_date.stream().map(element ->  element.getText()).collect(Collectors.toList());

    }

    @Then("I verify {string} playing is: {string}")
    public void i_verify_playing_is(String team, String isPlaying) {
        // Write code here that turns the phrase above into concrete actions
        if (isPlaying.equals("True")){
            Assert.assertTrue(teams_on_date.contains(team));
        }else {
            Assert.assertFalse(teams_on_date.contains(team));
        }
        driver.quit();
    }
}

// https://cucumber.io/docs/guides/10-minute-tutorial/

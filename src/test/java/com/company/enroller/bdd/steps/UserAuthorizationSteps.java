package com.company.enroller.bdd.steps;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserAuthorizationSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
    	System.setProperty("webdriver.chrome.driver", "lib/chromedriver-macos");
		driver = new ChromeDriver();
		driver.get("http://localhost:8088/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

    @After
    public void tearDown() {
        driver.quit();
    }
    
    @Given("^user goes to login page$")
    public void user_goes_to_login_page() throws Throwable {
    	driver.get("http://localhost:8088/");
    }
    
    @When("^user types \"([^\"]*)\" login$")
    public void user_types_login(String login) throws Throwable {
    	driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(login);
    }
    
    @When("^user types \"([^\"]*)\" password$")
    public void user_types_password(String password) throws Throwable {
    	driver.findElement(By.xpath("//input[@type='password']")).click();
    	driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(password);
    }

    @When("^user clicks \"([^\"]*)\" button$")
    public void user_clicks_button(String buttonText) throws Throwable {
    	//span[contains(text(),'New')]
    	driver.findElement(By.xpath("//button[@type='submit' and contains(., '"+buttonText+"')]")).click();
    	//driver.findElement(By.xpath("//span[contains(text(),'"+ buttonText +"')]")).click();
    	//driver.findElement(By.xpath("//button[@value=\""+buttonText+"\"]")).click();
    }
    
    @When("^user clicks \"([^\"]*)\" element$")
    public void user_clicks_element(String elementText) throws Throwable {
    	driver.findElement(By.xpath("//*[contains(., '"+elementText+"')]")).click();
    }

    @Then("^it should show error message stating that \"([^\"]*)\"$")  
    public void logging_error_message_is_displayed(String loggingErrorMessage) throws Throwable {
    	assertTrue(driver.getPageSource().contains(loggingErrorMessage));
    }
   
    
}

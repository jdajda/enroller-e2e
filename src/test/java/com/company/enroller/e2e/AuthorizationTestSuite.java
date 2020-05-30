package com.company.enroller.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class AuthorizationTestSuite {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver-macos");
		driver = new ChromeDriver();
		driver.get("http://localhost:8088/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		Files.copy(Paths.get("enroller-testing.db"), Paths.get("enroller.db"), StandardCopyOption.REPLACE_EXISTING);
	} 

	@Test
	public void SmokeTest() throws Exception {
		assertEquals("System do zapisów na zajęcia", driver.findElement(By.xpath("//div[@id='app']/h1")).getText());
	}

	@Test
	public void LoginInAndOut() throws Exception {
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("user");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		// WebElement logoutButton = new WebDriverWait(driver, 10)
		// .until(driver -> driver.findElement(By.id("btnlogout")));
//		driver.findElement(By.linkText("WYLOGUJ")).click();
		WebElement logoutButton = new WebDriverWait(driver, 10)
				.until(driver -> driver.findElement(By.linkText("WYLOGUJ")));
		logoutButton.click();
	}

	@Test
	public void LoginNotSuccessfull() throws Exception {
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("test");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("test");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='app']/div/div")).click();
		assertEquals("Logowanie nieudane.", driver.findElement(By.xpath("//div[@id='app']/div/div")).getText());
	}

	
	@Test
	public void RegisterExistingUser() throws Exception {
		driver.findElement(By.xpath("//div[@id='app']/div/button[2]")).click();
		driver.findElement(By.xpath("//input[@type='text']")).click();
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("user");
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("user");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//div[@id='app']/div/div")).click();
		try {
			assertEquals("Błąd przy zakładaniu konta. Kod odpowiedzi: 409",
					driver.findElement(By.xpath("//div[@id='app']/div/div")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}

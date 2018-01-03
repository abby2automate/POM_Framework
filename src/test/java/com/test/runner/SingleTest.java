package com.test.runner;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.businesslogic.LoginPage;
import com.test.utilities.Utility;

public class SingleTest extends Utility {

	public static Utility u;

	@Parameters({ "browser" })
	@BeforeTest()
	public void setUp(String browser) throws IOException {

		u = new Utility();

		// Initialize Properties File
		u.load_propertiesFile();

		// Initialize Log4j Objects
		u.load_log4j(browser);

		// Initialize ExtentReports Objects

	}

	@Parameters({ "browser" })
	@BeforeMethod()
	public void setBrowser(String browser) {

		System.out.println("------>>> THE BROWSER IS : " + browser + " <<<--------");

		u.load_browser(browser);

		// Browser Capabilities
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Launch Website
		u.load_url();
	}

	@Test(priority = 1, enabled = true, dataProviderClass = DataProviderUnit.class, dataProvider = "getTestData")
	public void login_Page_true(String username, String password) throws InterruptedException {

		log.info("---------->> In login_Page Method True <<----------");
		LoginPage lp = new LoginPage();
		lp.set_username(username);
		lp.set_password(password);
		String actual_title = lp.click_SignIn();
		assertEquals(actual_title, "Find a Flight: Mercury Tours:");
		log.info("---------->> In login_Page Method - True :: Completed <<----------");
		driver.findElement(By.linkText("SIGN-OFF")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("Home")).click();

	}

	@Test(priority = 2, enabled = false, dataProviderClass = DataProviderUnit.class, dataProvider = "getTestData")
	public void login_Page_false(String username, String password) throws InterruptedException {

		log.info("---------->> In login_Page Method - False <<----------");
		LoginPage lp = new LoginPage();
		lp.set_username(username);
		lp.set_password(password);
		String actual_title = lp.click_SignIn();
		assertEquals(actual_title, "Sign-on: Mercury Tours");
		log.info("---------->> In login_Page Method - False :: Completed <<----------");
	}

	@AfterMethod
	public static void teardown() throws Exception {
		driver.close();
		driver.quit();
	}

}

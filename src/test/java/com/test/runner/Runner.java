package com.test.runner;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.businesslogic.LoginPage;
import com.test.utilities.Utility;

public class Runner extends Utility {

	public static Utility u;

	@Parameters({ "browser" })
	@BeforeTest
	public void setUp(String browser) throws IOException {

		System.out.println("------>>> THE BROWSER IS : " + browser + " <<<--------");

		u = new Utility();

		// Initialize Properties File
		u.load_propertiesFile();

		// Initialize Log4j Objects
		u.load_log4j(browser);

		// Initialize ExtentReports Objects
		u.load_ExtentReports(browser);

		u.load_browser(browser);

		// Browser Capabilities
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Launch Website
		u.load_url();

	}

	@Test(priority = 1, enabled = true)
	public void login_Page_true() throws InterruptedException {

		extent_logger = extent.startTest("Login Page True");
		log.info("---------->> In login_Page Method True <<----------");
		LoginPage lp = new LoginPage();
		lp.set_username("tutorial");
		lp.set_password("tutorial");
		String actual_title = lp.click_SignIn();
		assertEquals(actual_title, "Find a Flight: Mercury Tours:");
		log.info("---------->> In login_Page Method - True :: Completed <<----------");

	}

	@Test(priority = 2, enabled = false)
	public void login_Page_false() throws InterruptedException {

		extent_logger = extent.startTest("Login Page False");
		log.info("---------->> In login_Page Method - False <<----------");
		LoginPage lp = new LoginPage();
		lp.set_username("test");
		lp.set_password("test");
		String actual_title = lp.click_SignIn();
		assertEquals(actual_title, "Find a Flight: Mercury Tours:");
		log.info("---------->> In login_Page Method - False :: Completed <<----------");
	}

	@Test(enabled = false)
	public void smokeTest_URLs() throws IOException {
		int counter_ok = 0;
		int counter_broken = 0;
		List<WebElement> links = driver.findElements(By.tagName("a"));
		links.addAll(driver.findElements(By.tagName("img")));

		List<WebElement> active_links = new ArrayList<WebElement>();

		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getAttribute("href") != null
					&& (!links.get(i).getAttribute("href").contains("javascript"))) {
				active_links.add(links.get(i));
			}
		}

		System.out.println("Total Active Links : " + active_links.size());

		for (int j = 0; j < active_links.size(); j++) {

			HttpURLConnection connection = (HttpURLConnection) new URL(active_links.get(j).getAttribute("href"))
					.openConnection();
			connection.connect();
			String response = connection.getResponseMessage();
			connection.disconnect();
			if (response.equalsIgnoreCase("OK")) {
				counter_ok++;
			} else {
				counter_broken++;
			}
			System.out.println(active_links.get(j).getAttribute("href") + " -->>" + response);

		}

		System.out.println("Links_Working : " + counter_ok);
		System.out.println("Links_Not Working : " + counter_broken);

	}

	@AfterMethod
	public static void getResult(ITestResult result) throws Exception {
		System.out.println("The Result is : " + result);
		if (result.getStatus() == ITestResult.FAILURE) {
			extent_logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			extent_logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			String screenshotPath = Utility.getScreenshot(result.getName());
			System.out.println("The ScreenShot Path is : " + screenshotPath);
			extent_logger.log(LogStatus.FAIL, extent_logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			extent_logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			boolean testr = result.getStatus() == ITestResult.SUCCESS;
			System.out.println(testr);
			System.out.println("Method name is : " + result.getName());
			LogStatus l = LogStatus.PASS;
			extent_logger.log(LogStatus.PASS, "Test Case Passed is" + result.getName());
		}
		extent.endTest(extent_logger);

	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
		driver.close();
		driver.quit();

	}

}

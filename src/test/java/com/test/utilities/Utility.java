package com.test.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Parameters;
import org.apache.commons.io.FileUtils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.test.businesslogic.LoginPage;

public class Utility {

	public static WebDriver driver;
	public static Logger log;
	public static ExtentReports extent;
	public static ExtentTest extent_logger;
	public static Properties p;
	public static String browser;

	public void load_propertiesFile() throws IOException {
		File f = new File("C:\\Automation_FrameWorks\\POM_Framework\\src\\test\\resources\\Config.properties");
		FileReader fr = new FileReader(f);
		p = new Properties();
		p.load(fr);
	}

	public void load_log4j(String browser) {
		log = LogManager.getLogger(Utility.class.getName());
		log.info(
				"===================================================================================================================");
		log.info(
				"                                         Testing Execution Starts                                                  ");
		log.info(
				"===================================================================================================================");
		log.info("The Browser is : " + browser + "The Thread is : " + Thread.currentThread().getId());

	}

	public void load_ExtentReports(String browser) {
	//	extent = new ExtentReports("C:\\Automation_FrameWorks\\ExtentReports\\" + browser + "_ExtentReport.html", true);
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/ExtentReport.html", true);
		extent.addSystemInfo("Host Name", "FrameWork").addSystemInfo("Environment", "Windows")
				.addSystemInfo("User Name", "Appu");
		extent.loadConfig(new File(p.getProperty("extent-configFile")));

	}

	public void load_browser(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			log.info("Thread is : " + Thread.currentThread().getId()
					+ " ---------->> Starting to Load Chrome Browser <<----------");
			log.info("Class Details : com.test.utilities.Utility.load_chrome()");
			System.setProperty(p.getProperty("chrome_driver"), p.getProperty("chrome_driverlocation"));
			driver = new ChromeDriver();
			log.info("Thread is : " + Thread.currentThread().getId()
					+ "---------->> Chrome Browser Loaded <<----------");
		} else if (browser.equalsIgnoreCase("firefox")) {
			log.info("Thread is : " + Thread.currentThread().getId()
					+ " ---------->> Starting to Load FireFox Browser <<----------");
			log.info("Class Details : com.test.utilities.Utility.load_firefox()");
			System.setProperty(p.getProperty("firefox_driver"), p.getProperty("firefox_driverlocation"));
			driver = new FirefoxDriver();
			log.info("Thread is : " + Thread.currentThread().getId()
					+ "---------->> FireFox Loaded Browser <<----------");
		} else if (browser.equalsIgnoreCase("iexplorer")) {
			log.info("Thread is : " + Thread.currentThread().getId()
					+ " ---------->> Starting to Load IntenetExplorer Browser <<----------");
			log.info("Class Details : com.test.utilities.Utility.load_ie()");
			System.setProperty(p.getProperty("InternetExp_driver"), p.getProperty("InternetExp_driverlocation"));
			driver = new InternetExplorerDriver();
			log.info("Thread is : " + Thread.currentThread().getId()
					+ " ---------->> IntenetExplorer Browser Loaded <<----------");

		} /*
			 * else if (browser.equalsIgnoreCase("htmlunit")) {
			 * log.info("Thread is : " + Thread.currentThread().getId() +
			 * " ---------->> Starting to Load HTML_Unit Browser <<----------");
			 * log.info("Class Details : com.test.utilities.Utility.load_ie()");
			 * System.setProperty(p.getProperty("chrome_driver"),
			 * p.getProperty("chrome_driverlocation")); driver = new
			 * HtmlUnitDriver(); log.info("Thread is : " +
			 * Thread.currentThread().getId() +
			 * " ---------->> HTML_Unit Browser Loaded <<----------"); }
			 */

		else if (browser.equalsIgnoreCase("chrome_headless")) {
			log.info("Thread is : " + Thread.currentThread().getId()
					+ " ---------->> Starting to Load Chrome Headless Browser <<----------");
			log.info("Class Details : com.test.utilities.Utility.load_ie()");
			System.setProperty(p.getProperty("chrome_driver"), p.getProperty("chrome_driverlocation"));
			ChromeOptions options = new ChromeOptions();
			options.addArguments("window-size=1400,800");
			options.addArguments("headless");
			driver = new ChromeDriver(options);
			log.info("Thread is : " + Thread.currentThread().getId()
					+ " ---------->> Chrome Headless Browser Loaded <<----------");
		}

	}

	public void load_url() {
		log.info("Thread is : " + Thread.currentThread().getId() + " ---------->> Starting to Load URL <<----------");
		log.info("Class Details : com.test.utilities.Utility.load_url()");
		driver.get("http://newtours.demoaut.com/");
		log.info("Thread is : " + Thread.currentThread().getId() + " ---------->> URL Loaded <<----------");
	}

	public static String getScreenshot(String screenshotName) throws Exception {

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		/*
		 * String destination = System.getProperty("user.dir") +
		 * "/FailedTestsScreenshots/" + screenshotName + dateName + ".png";
		 */

		String destination = p.getProperty("ScreenShot_Destination") + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}

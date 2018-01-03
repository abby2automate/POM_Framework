package com.test.runner;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.businesslogic.LoginPage;
import com.test.utilities.Utility;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

public class DataDrivenTest_DB extends Utility {

	public static Utility u;
	public static Connection con;
	public static Statement stmnt;
	public static ResultSet result;

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

		String host = "localhost";
		String port = "3306";
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/TestDB", "root", "admin");

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

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

	@Test(priority = 1, enabled = true)
	public void login_Page_true() throws InterruptedException {

		log.info("---------->> In login_Page Method True <<----------");
		LoginPage lp = new LoginPage();

		try {

			String query = "select * from userdetails where id='1'";
			stmnt = con.createStatement();
			result = stmnt.executeQuery(query);

			while (result.next()) {
				lp.set_username(result.getString("uname"));
				lp.set_password(result.getString("pass"));
				String actual_title = lp.click_SignIn();
				assertEquals(actual_title, "Find a Flight: Mercury Tours:");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("---------->> In login_Page Method - True :: Completed <<----------");

	}

	@Test(priority = 2, enabled = true)
	public void login_Page_false() throws InterruptedException{

		log.info("---------->> In login_Page Method - False <<----------");
		LoginPage lp = new LoginPage();
		try {
			
			String query = "select * from userdetails where id='2'";
			stmnt = con.createStatement();
			result = stmnt.executeQuery(query);
			
			while (result.next()) {
				lp.set_username(result.getString("uname"));
				lp.set_password(result.getString("pass"));
				String actual_title = lp.click_SignIn();
				assertEquals(actual_title, "Sign-on: Mercury Tours");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		log.info("---------->> In login_Page Method - False :: Completed <<----------");
	}

	@AfterMethod
	public static void teardown() throws Exception {
		driver.close();
		driver.quit();
	}

}

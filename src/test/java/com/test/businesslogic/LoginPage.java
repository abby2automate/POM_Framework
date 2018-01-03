package com.test.businesslogic;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.test.pageobjects.PO_Login;
import com.test.runner.Runner;
import com.test.utilities.Utility;

public class LoginPage extends Utility {
	
	PO_Login login = new PO_Login();
	
	public void set_username(String username) {
		log.info("Thread is : "+Thread.currentThread().getId()+"In set_username Method");
		log.info("Class Details : com.test.businesslogic.LoginPage.set_username(String username)");
		log.debug("UserName Send is : "+username);
		login.user_name.sendKeys(username);
		log.info("Thread is : "+Thread.currentThread().getId()+"Method Completed");
	
	}

	public void set_password(String password) {
		log.info("Thread is : "+Thread.currentThread().getId()+"In set_password Method");
		log.info("com.test.businesslogic.LoginPage.set_password(String password)");
		login.password.sendKeys(password);
		log.info("Thread is : "+Thread.currentThread().getId()+"Method Completed");
	}

	public String click_SignIn() throws InterruptedException {
		log.info("Thread is : "+Thread.currentThread().getId()+"In click_SignIn Method");
		log.info("com.test.businesslogic.LoginPage.click_SignIn()");
		login.sign_in.click();
		String title = driver.getTitle();
		Thread.sleep(5000);
		log.info("Thread is : "+Thread.currentThread().getId()+"Method Completed");
		return title;
	}
}

package com.test.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.runner.Runner;
import com.test.utilities.Utility;

public class PO_Login extends Utility {
	
	public PO_Login()
	{
		super();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(name = "userName")
	public WebElement user_name;
	

	@FindBy(name = "password")
	public WebElement password;
	
	
	@FindBy(name = "login")
	public WebElement sign_in;
}

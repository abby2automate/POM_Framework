package com.test.runner;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.test.utilities.Utility;

public class DataProviderUnit {
	
	@DataProvider
	public Object[][] getTestData() throws IOException
	{
		Object[][] data = Utility.getTestData("TestData.xlsx","testdata");
		return data;
	}
}

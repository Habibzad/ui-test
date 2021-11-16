package com.swaglabs.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public abstract class Base {

	public static WebDriver driver;

	@BeforeClass(alwaysRun = true)
	public void init() throws Throwable {
		System.out.println("Before Class Executed");
		
		UtilMethods utilMethods = new UtilMethods(driver);
		driver = utilMethods.startBrowser(Drivers.CHROME);
	}

	@BeforeMethod
	public void openBrowser() {
		System.out.println("Before Method Executed");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

//	@AfterMethod
//	public void closeBrowser() {
//		System.out.println("After Method Executed");
//		UtilMethods.customWait(1);//Wait for 1 second before closing the browser
//		driver.close();
//	}

	@AfterClass
	public void clearOut() {
		System.out.println("After Class Executed");
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}
}

package com.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.swaglabs.util.Base;
import com.swaglabs.util.UtilMethods;

public class LoginPage extends Base{
	
	private UtilMethods utilMethods = new UtilMethods(driver);
	
	public void openHomePage() {
		System.out.println("open home page");
		driver.get("https://www.saucedemo.com/inventory.html");
	}
	
	public LoginPage login() {
		System.out.println("login");
		WebElement username = driver.findElement(By.cssSelector("input[placeholder='Username']"));
		WebElement password = driver.findElement(By.name("password"));
		WebElement btn = driver.findElement(By.id("login-button"));
		
		username.sendKeys("standard_user");
		password.sendKeys("secret_sauce");
		btn.click();
		
		String homePageUrl = "https://www.saucedemo.com/inventory.html";
		Assert.assertEquals(driver.getCurrentUrl(), homePageUrl);
		return this;
	}
	
	public void options() {
		System.out.println("options button clicked ");
		By options = By.id("react-burger-menu-btn");
		utilMethods.clickButton(options);
	}
	
	public void logout() {
		System.out.println("page logout button clicked ");
		By logoutBtn = By.id("logout_sidebar_link");
		utilMethods.clickButton(logoutBtn);
		String loginPageUrl = "https://www.saucedemo.com/";
		Assert.assertEquals(driver.getCurrentUrl(), loginPageUrl);
	}
}

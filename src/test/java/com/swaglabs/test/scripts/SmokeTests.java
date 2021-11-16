package com.swaglabs.test.scripts;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.swaglabs.pages.HomePage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.util.Base;

public class SmokeTests extends Base{
	
	LoginPage login;
	HomePage home;
	
	@Test (priority = 1)
	public void testLogin() {
		login = new LoginPage();
		login.openHomePage();
		login.login();
	}
	
	@Test (priority = 2)
	public void selectItem() {
		home = new HomePage();
		home.selectItem();
		home.checkOut();
	}

}

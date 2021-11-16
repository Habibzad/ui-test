package com.swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.swaglabs.util.Base;
import com.swaglabs.util.UtilMethods;

public class HomePage extends Base{
		
	public void selectItem() {
		WebElement fleeseJacket = driver.findElement(By.id("item_5_img_link"));
		fleeseJacket.click();
		String url = "https://www.saucedemo.com/inventory-item.html?id=5";
		Assert.assertEquals(driver.getCurrentUrl(), url);
		UtilMethods.customWait(1);//Wait for 1 second before closing the browser
		WebElement addBtn = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
		addBtn.click();
		WebElement cart = driver.findElement(By.className("shopping_cart_badge"));
		String cartValue = cart.getText();
		System.out.println("cart value:" +cartValue);
		cart.click();
		WebElement checkOut = driver.findElement(By.id("checkout"));
		checkOut.click();
		
		UtilMethods.customWait(2);
	}
	
	public void checkOut() {
		WebElement firstName = driver.findElement(By.id("first-name"));
		WebElement lastName = driver.findElement(By.id("last-name"));
		WebElement zipCode = driver.findElement(By.id("postal-code"));
		WebElement continueBtn = driver.findElement(By.id("continue"));
		
		firstName.sendKeys("John");
		lastName.sendKeys("Doe");
		zipCode.sendKeys("20123");
		UtilMethods.customWait(1);
		continueBtn.click();
		
		WebElement finish = driver.findElement(By.id("finish"));
		finish.click();
		UtilMethods.customWait(1);
	}

}

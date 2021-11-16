package com.swaglabs.util;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.google.common.io.Files;

public class UtilMethods {

	WebDriver driver;

	public UtilMethods(WebDriver _driver) {
		this.driver = _driver;
	}

	public WebDriver startBrowser(Drivers browserType) {
		if (browserType == Drivers.CHROME) {
			driver = startChromeBrowser();
		} else if (browserType == Drivers.FIRE_FOX) {
			driver = startFirefoxBrowser();
		} else if (browserType == Drivers.EDGE) {
			startEdgeBrowser();
		} else {
			System.out.println(browserType + "browser is not support");
			System.out.println("starting default browser 'Chrome'");
			driver = startChromeBrowser();
		}
		driver.manage().deleteAllCookies();
		return driver;
	}

	private WebDriver startChromeBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/browser_drivers/chromedriver.exe");
		driver = new ChromeDriver(); //
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver startFirefoxBrowser() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/browser_drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	private WebDriver startEdgeBrowser() {
		System.setProperty("webdriver.gecko.driver", "src/test/resources/browser_drivers/msedgedriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}
	
	public WebElement findElement(By by) {
		WebElement element = driver.findElement(by);
		return element;
	}

	public void selectDropDown(By by, int index) {
		WebElement element = driver.findElement(by);
		Select dropDown = new Select(element);
		dropDown.selectByIndex(index);
	}

	public void selectDropDown(By by, String visibleText) {
		WebElement element = driver.findElement(by);
		Select dropDown = new Select(element);
		dropDown.selectByVisibleText(visibleText);
	}

	public void selectDropDown(String attributeValue, By by) {
		WebElement element = driver.findElement(by);
		Select dropDown = new Select(element);
		dropDown.selectByValue(attributeValue);
	}

	public void enterText(By by, String textString) {
		WebElement element = driver.findElement(by);
		element.clear();
		element.sendKeys(textString);
	}

	public void clickButton(By by) {
		WebElement button = driver.findElement(by);
		button.click();
	}

	public void clickHiddenElement(By by) {
		WebElement elem = driver.findElement(by);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", elem);
	}

	public static void customWait(double inSecs) {
		try {
			Thread.sleep((long) (inSecs * 1000));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveMouseToElement(WebElement targetElem) {
		Actions action = new Actions(driver);
		action.moveToElement(targetElem).build().perform();
	}

	public void moveMouseToElement(WebElement firstElem, WebElement secElem) {
		Actions action = new Actions(driver);
		action.moveToElement(firstElem).build().perform();
		customWait(1);
		action.moveToElement(secElem).build().perform();
		customWait(1);
	}

	public void highlightElement(WebElement element) {
		for (int i = 0; i < 4; i++) {

			WrapsDriver wrappedElement = (WrapsDriver) element;
			JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: red; border: 2px solid yellow");

			customWait(0.5);

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			customWait(0.5);
		}
	}

	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public void scrollByVertically(String verticalPixel) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0," + verticalPixel + ")");
	}

	public void scrollByHorizontally(String horizontalPixel) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(" + horizontalPixel + ",0)");
	}

	public void sendKeyBoard(CharSequence... keysToSend) {
		WebElement webpage = driver.findElement(By.tagName("body"));
		webpage.sendKeys(keysToSend);
	}

	public String getCurrentTime() {
		String finalTime = null;

		Date date = new Date();
		String tempTime = new Timestamp(date.getTime()).toString();
		// System.out.println("time: " + tempTime);
		finalTime = tempTime.replace("-", "").replace(" ", "").replace(":", "").replace(".", "");
		// System.out.println("updated time: " + finalTime);
		return finalTime;
	}

	public String screenCapture(String screenshotFileName) {
		String filePath = null;
		String fileName = null;
		try {
			fileName = screenshotFileName + getCurrentTime() + ".png";
			filePath = "target/screenshots/";
			File tempfile = new File(filePath);
			if (!tempfile.exists()) {
				tempfile.mkdirs();
			}
			filePath = tempfile.getAbsolutePath();

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(filePath + "/" + fileName));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath + "/" + fileName;
	}

	public WebElement waitForElementPresence(By by) {
		WebElement elem = null;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return elem;
	}

	public WebElement waitForElementVisibility(By by) {
		WebElement elem = null;
		elem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(by));
		return elem;
	}

	public WebElement waitForElementToBeClickable(By by) {
		WebElement elem = null;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		elem = wait.until(ExpectedConditions.elementToBeClickable(by));
		return elem;
	}

	public WebElement fluentWait(final By by) {
		WebElement targetElem = null;
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		targetElem = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
		return targetElem;
	}

	public void handleCheckBox(By by, boolean isCheck) {
		WebElement checkBoxElem = driver.findElement(by);
		boolean checkBoxState = checkBoxElem.isSelected();

		// Scenario: 1 ===> User wants to check the check-box
		if (isCheck == true) {
			// check-box state 1: check box is checked already
			if (checkBoxState == true) {
				// do nothing
				// System.out.println("Scenario 1, state 1");
				// System.out.println("checkbox is already selected");
			}
			// check-box state 2: check box is not checked
			else {
				// select the check-box
				// System.out.println("Scenario 1, state 2");
				checkBoxElem.click();
			}
		}
		// Scenario: 2 ===> User wants to uncheck the check-box
		else {
			// check-box state 1: check box is checked already
			if (checkBoxState == true) {
				// select the check- box
				// System.out.println("Scenario 2, state 1");
				checkBoxElem.click();
			} else
			// check-box state 2: check box is not checked
			{
				// do nothing
				// System.out.println("Scenario 2, state 2");
				// System.out.println("checkbox is not checked");
			}
		}
	}
}

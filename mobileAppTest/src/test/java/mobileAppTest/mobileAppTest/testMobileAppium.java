package mobileAppTest.mobileAppTest;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testMobileAppium {
    public static AppiumDriver driver;

    @SuppressWarnings("deprecation")
	@BeforeMethod
    public void setup() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setUdid("emulator-5554");
        options.setAppPackage("com.google.android.youtube");
        options.setAppActivity("com.google.android.youtube.app.honeycomb.Shell$HomeActivity");
        options.setDeviceName("Pixel 4 API 33");
        options.setAutomationName("UiAutomator2");
        options.setPlatformVersion("13");
        options.setAutoGrantPermissions(true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(description = "Test to play my favorite music on Youtube")
    public void testPlayMyFavoriteMusicOnYouTube() {
        // wait for search icon
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Search")));
    	searchIcon.click();

        // Send keys to search bar
        WebElement searchBar = driver.findElement(By.id("com.google.android.youtube:id/search_edit_text"));
        searchBar.sendKeys("Rick Rolled (Short Version)");

        // Click on search suggestion (assuming unique text)
        WebElement searchSuggestion = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='rick rolled (short version)']")));
        searchSuggestion.click();

        // Click on search result (assuming unique title)
        WebElement searchResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.ViewGroup[contains(@content-desc, 'Rick Rolled (Short Version)')]")));
        searchResult.click();

        // Click on expand video's description
        WebElement expandButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='Expand description']/android.widget.ImageView")));
        expandButton.click();

        // Assertion - assert that the uploader name Legacy PNDA is visible
        WebElement uploaderName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.ViewGroup[@content-desc='Legacy PNDA']")));
        Assert.assertTrue(uploaderName.isDisplayed(), "Uploader name Legacy PNDA is not visible!");
    }
}
package mobileAppTest.mobileAppTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import java.net.MalformedURLException;
import static org.junit.Assert.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.By;
import io.appium.java_client.android.AndroidUIAutomator;
import java.util.List;

public class TestSearchRestaurantByName {
    private AndroidDriver<MobileElement> driver;
    
    @BeforeClass
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("platformVersion", "11.0");
        capabilities.setCapability("appPackage", "com.ubercab");
        capabilities.setCapability("appActivity", ".activity.MainActivity");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
        
        //Assuming user is already logged in
        MobileElement emailField = driver.findElementByAccessibilityId("Email address input field");
        emailField.sendKeys("testuser@gmail.com");
        
        MobileElement passField = driver.findElementById("password_input_field");
        passField.sendKeys("Password123!");
        
        MobileElement signInBtn = driver.findElementByXPath("//android.widget.Button[@content-desc='Log in']");
        signInBtn.click();
    }
    
    @Test
    public void verifyRestaurantDetailsPageOpensAfterSearching() {
        //Navigate To Search Bar & Clear Text
        MobileElement searchBar = driver.findElementById("search_src_text");
        searchBar.clear();
        
        //Type In Restaurant Name
        searchBar.sendKeys("Burger King");
        
        //Select Suggestion From Drop Down List
        MobileElement suggestion = driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).getChildByText(new UiSelector().className(\"android.widget.TextView\"), \"Burger King\", false)");
        suggestion.click();
        
        //Wait For Restaurant Details Page To Load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu")));
        
        //Check If Menu Is Visible
        Assert.assertEquals(driver.findElementById("menu").isDisplayed(), true);
    }
}
package LoginTest;

import PageObjects.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class LoginTest {
    public String baseURL = "https://antonregis.com/user";
    public WebDriver driver;

    @BeforeTest
    public void setupDriver() {
        System.setProperty("webdriver.chrome.driver", "D:/Automation/Libraries/Selenium/Chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test()
    // Successful login test
    public void login01_Successful_Login() throws InterruptedException {
        performLogin(driver, "validUser", "1234567890", baseURL);
        String successfulLoginStatus = LoginPage.loginMessageStatus(driver).getText();
        String expectedMessage = "Status message\nLog in successful for validUser.";
        Assert.assertEquals(successfulLoginStatus, expectedMessage);
        Thread.sleep(5000);
        LoginPage.logoutButton(driver).click();
    }

    @Test()
    // Failed login test
    public void login02_Failed_Login() throws InterruptedException {
        performLogin(driver, "invalidUsername", "password", baseURL);
        String failedLoginStatus = LoginPage.loginMessageStatus(driver).getText();
        String expectedMessage = "Error message\nSorry, unrecognized username or password. Have you forgotten your password?";
        Assert.assertEquals(failedLoginStatus, expectedMessage);
        Thread.sleep(5000);
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }


    public static void performLogin(WebDriver driver, String username, String password, String baseURL) {
        driver.get(baseURL);
        LoginPage.username(driver).sendKeys(username);
        LoginPage.password(driver).sendKeys(password);
        LoginPage.loginButton(driver).click();
    }
}
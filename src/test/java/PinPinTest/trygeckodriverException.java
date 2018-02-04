package PinPinTest;

import PinPinTest.PageElements.LoginPage;
import PinPinTest.Tools.DriverListener;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class trygeckodriverException {
    EventFiringWebDriver driver;

    @Test(expectedExceptions = NoSuchElementException.class)
    public void test() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");

        WebDriver edriver = new FirefoxDriver();
        driver = new EventFiringWebDriver(edriver);
        DriverListener handler = new DriverListener();
        driver.register(handler);
        driver.get("https://www.pinpineat.com/");
        LoginPage loginPage=new LoginPage(driver);

        try{
            loginPage.loginButton.getAttribute("disabled");
        }catch (TimeoutException e){
            System.out.println("catch exception of selenium timeOutExcption");
        }finally{
            System.out.println("here is finally process");
        }

    }
    @AfterTest
    public void afterTest() {
        try {
            driver.close();
            driver.quit();
        }catch (SessionNotCreatedException e){
            System.out.println("catched session not created exception");
        }
    }
}

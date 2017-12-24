package PinPinTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class trygeckodriver {
    @Test
    public void test() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver","src/test/resources/drivers/geckodriver.exe");
        WebDriver  driver=new FirefoxDriver();
        driver.get("https://www.google.com/");
        Thread.sleep(5000);
        driver.quit();
    }
}

package PinPinTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class openurl {
    WebDriver driver;
    @Test
    public void open(){
        String osName= (System.getProperty("os.name"));

        if (osName.equalsIgnoreCase("Mac OS X"))
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        else   System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");

        ChromeOptions chromeOptions =new ChromeOptions();
        chromeOptions.addArguments("--kiosk");
        driver= new ChromeDriver(chromeOptions);
        driver.get("https://pinpineat.com");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @AfterTest
    public  void tearDown() throws Exception{
        driver.quit();
    }
}

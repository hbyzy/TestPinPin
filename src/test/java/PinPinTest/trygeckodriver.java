package PinPinTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class trygeckodriver {
    @Test
    public void test() throws InterruptedException {
       String browserName=System.getProperty("broswerName");
       System.out.println("test resulte:"+browserName);
    }
}

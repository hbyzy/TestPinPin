package PinPinTest.Prepare;


import PinPinTest.Tools.DriverListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.beans.EventHandler;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class PinPinTestPrepare implements Interactive {
    public static WebDriver edriver;
    public EventFiringWebDriver driver;
    String baseUrl;
    int timeout = 5000;
    int interval = 500;

    public PinPinTestPrepare(String browserName) {
        String osName = (System.getProperty("os.name"));

        if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
//        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
//        capabilities.setCapability("marionette", true);
            WebDriver edriver = new FirefoxDriver();
            driver = new EventFiringWebDriver(edriver);
            DriverListener handler = new DriverListener();
            driver.register(handler);
        } else if (browserName.equals("chrome")) {
            if (osName.equalsIgnoreCase("Mac OS X"))
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
            else System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--kiosk");

            edriver = new ChromeDriver(chromeOptions);
            driver = new EventFiringWebDriver(edriver);
            DriverListener handler = new DriverListener();
            driver.register(handler);
        }
    }


    // public PinPinTestPrepare(){
//
//        if (osName.equalsIgnoreCase("Mac OS X"))
//            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
//        else   System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
//
//        ChromeOptions chromeOptions =new ChromeOptions();
//        chromeOptions.addArguments("--kiosk");
//
//        edriver= new ChromeDriver(chromeOptions);
//        System.setProperty("webdriver.gecko.driver","src/test/resources/drivers/geckodriver.exe");
////        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
////        capabilities.setCapability("marionette", true);
//        WebDriver  edriver=new FirefoxDriver();
//        driver=new EventFiringWebDriver(edriver);
//        DriverListener handler = new DriverListener();
//        driver.register( handler);
//    }

    public void pageLoad(String url) {
        baseUrl = url;
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void tearDown() {
        driver.close();
        driver.quit();
    }

    public void rolldown(int x, int y) {
        JavascriptExecutor jse = driver;
        //jse.executeScript("windows.scrollby(0.250)","");
        // String jsstr="'windows.scrollby('+x+','+y)";
        String jsstr = "scroll(" + x + ',' + y + ")";
        jse.executeScript(jsstr);
    }

    public String gotSubStr(String str) {
        String arr[] = str.split("\"");
        return arr[1];
    }

    @Override
    public void perform(Collection<Sequence> collection) {

    }

    @Override
    public void resetInputState() {

    }
}

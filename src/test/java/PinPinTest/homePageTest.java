package PinPinTest;

import PinPinTest.PageElements.pageElements;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class homePageTest {
    WebDriver driver;
    PinPinTestPrepare ppp=new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    pageElements pageEl;
    WebDriverWait wait;
    @BeforeTest
    public void beforeTest() throws InterruptedException {
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/");
        testAssert = new PinPinAssert(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        pageEl= PageFactory.initElements(driver,pageElements.class );
                new pageElements(driver);
        wait = new WebDriverWait(driver, 20);
        Boolean pageLoad = testAssert.PageChangeAssert("Pinpin Eat");
        if (!pageLoad)
            System.out.println("can not load homePage");
        else {
            System.out.println("we are in homepage now");
        }
    }
    @Test
    public void clickLoginBtn(){

    }
}

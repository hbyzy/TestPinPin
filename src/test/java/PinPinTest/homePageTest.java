package PinPinTest;

import PinPinTest.PageElements.HomePage;
import PinPinTest.PageElements.LoginPage;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;

public class homePageTest {
    WebDriver driver;
    PinPinTestPrepare ppp = new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    HomePage homePage;
    LoginPage loginPage;
    WebDriverWait wait;

    @BeforeMethod
    public void beforeTest() throws InterruptedException {
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/");
        testAssert = new PinPinAssert(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, 20);
        Boolean pageLoad = testAssert.PageChangeAssert("Pinpin Eat");
        if (!pageLoad)
            System.out.println("can not load homePage");
        else {
            System.out.println("we are in homepage now");
        }
    }

    @Test
    public void clickLoginBtn() {
        homePage.loginBtn.click();
        WebElement log = findElement.FindElementWait(loginPage.LoginTxt, 2);
        Assert.assertEquals(log.getText(), "Login");
        System.out.println(log.getText().equals("Login") ? "change to login window" : "something wrong when click login button");
    }

    @Test
    public void basketClick() {
        homePage.basket.click();
        findElement.FindElementWait(By.id("try"), 3);
        Alert a1 = driver.switchTo().alert();
        System.out.println(a1.getText());
        a1.accept();
        WebElement log = findElement.FindElementWait(loginPage.LoginTxt, 2);
        Assert.assertEquals(log.getText(), "Login");
        System.out.println(log.getText().equals("Login") ? "change to login window by alert " : "something wrong when click basket button");
    }

    //http://www.cnblogs.com/rosepotato/p/4118203.html
    @AfterMethod
    public void tearDown() {
    driver.close();
}
}

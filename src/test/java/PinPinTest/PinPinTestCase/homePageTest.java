package PinPinTest.PinPinTestCase;

import PinPinTest.PageElements.HomePage;
import PinPinTest.PageElements.LoginPage;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;

import org.apache.xpath.operations.Bool;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;

import java.util.Set;

public class homePageTest {
    WebDriver driver;
    PinPinTestPrepare ppp = new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    HomePage homePage;
    LoginPage loginPage;
    WebDriverWait wait;

    @BeforeTest
    public void beforeTest() {
        driver = ppp.driver;

        testAssert = new PinPinAssert(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, 20);

    }

    @BeforeMethod
    public void BeforeMethod() throws InterruptedException {
        ppp.pageLoad("https://www.pinpineat.com/");
        Boolean pageLoad = testAssert.PageChangeAssert("Pinpin Eat");
        if (!pageLoad)
            System.out.println("before method: can not load homePage");
        else {
            System.out.println("before method : we are in homepage now");
        }
    }

    @Test(enabled = false)
    public void clickLoginBtn() {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);

        homePage.loginBtn.click();
        WebElement log = findElement.FindElementWait(loginPage.LoginTxt, 2);
        Assert.assertEquals(log.getText(), "Login");
        System.out.println(log.getText().equals("Login") ? "change to login page" : "something wrong when click login button");
    }

    @Test(enabled = true)
    public void basketClick() {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);

        homePage.basket.click();
        findElement.FindElementWait(By.id("try"), 3);
        Alert a1 = driver.switchTo().alert();
        System.out.println("alert shows: " + a1.getText());
        a1.accept();
        WebElement log = findElement.FindElementWait(loginPage.LoginTxt, 2);
        Assert.assertEquals(log.getText(), "Login");
        System.out.println(log.getText().equals("Login") ? "change to login window by alert " : "something wrong when click basket button");
    }

    @Test(enabled = false)
    public void translateTestChinese() {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);
        homePage.translate.click();
        findElement.FindElementWait(homePage.chinese, 1);
        homePage.chinese.click();
        findElement.FindElementWait(homePage.loginText, 1);
        Assert.assertEquals(homePage.loginText.getText(), "账户");
    }

    @Test(enabled = false)
    public void translateTestFrench() {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);
        homePage.translate.click();
        findElement.FindElementWait(homePage.chinese, 1);
        homePage.french.click();
        findElement.FindElementWait(homePage.loginText, 1);
        Assert.assertEquals(homePage.loginText.getText(), "Compte");
    }

    @Test(enabled = false)
    public void translateTestEnglish() {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);

        homePage.translate.click();
        findElement.FindElementWait(homePage.chinese, 1);
        homePage.english.click();
        findElement.FindElementWait(homePage.loginText, 1);
        Assert.assertEquals(homePage.loginText.getText(), "Login");
    }

    @Test(enabled = false)
    public void appleStore() throws InterruptedException {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);
        homePage.appleStore.click();
        Assert.assertTrue(testAssert.PageChangeAssert("Pinpin Eat -Montreal Asian food delivery 拼拼点餐 on the App Store"));
        System.out.println("apple store assert right");
    }
    @Test(enabled = false)
    public void playStore() {
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);

        homePage.playStore.click();
        //WebElement we=driver.findElement(By.xpath("//*[@id='body-content']/div/div/div[1]/div[1]/div/div[1]/div/div[2]/div[2]/div[2]/div/a[1]/span"));
        findElement.FindElementWait(homePage.playStoreTitle,1);
        Assert.assertEquals(homePage.playStoreTitle.getText(),"Pinpin Market");
            System.out.println("playStore assert right");
    }
    @Test(enabled=false)
    public void needDriverClick(){
        System.out.println("---------------------------------------");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: "+methodName);

        ppp.rolldown(0,1800);
        Actions action =new Actions(driver);
        findElement.FindElementWait(homePage.needDriver,1 );
        action.moveToElement(homePage.needDriver).click().perform();

        findElement.FindElementWait(homePage.needDriverAlertText,1 );
        Assert.assertEquals(homePage.needDriverAlertText.getText(),"Please send your resume to: info@pinpinmarket.com" );
        homePage.needDriverAlertBtn.click();
        System.out.println(methodName+" :"+"assert right");
    }

    //http://www.cnblogs.com/rosepotato/p/4118203.html
    @AfterMethod
    public void afterMethod() {
        System.out.println("one test finished, go to next test or test finish soon");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
package PinPinTest.PinPinTestCase;

import PinPinTest.PageElements.HomePage;
import PinPinTest.PageElements.LoginPage;
import PinPinTest.PageElements.UserPage;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LoginPageTest {
    WebDriver driver;
    PinPinTestPrepare ppp = new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    LoginPage loginPage;
    UserPage userPage;

    WebDriverWait wait;

    @DataProvider(name = "login")
    public Object[][] loginParameter() {
        Object[][] loginData = new Object[3][2];
        loginData[0][0] = "12@12.com";
        loginData[0][1] = "123456";
        loginData[1][0] = "aaa@aaa.com";
        loginData[1][1] = "aaaa";
        loginData[2][0] = "qqqqqqqq";
        loginData[2][1] = "12333";
        return loginData;
    }

    @BeforeTest
    public void beforeTest() {
        driver = ppp.driver;

        testAssert = new PinPinAssert(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        loginPage = new PinPinTest.PageElements.LoginPage(driver);
        userPage = new UserPage(driver);
        wait = new WebDriverWait(driver, 20);

    }

    @BeforeMethod
    public void BeforeMethod() {
        ppp.pageLoad("https://pinpineat.com/#!/login/");
    }

    @Test(enabled = true, dataProvider = "login")
    public void testLoginFunction(String email, String passWord) {
        System.out.println("---------------------------------------");
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: " + methodName + " with " + email + " , " + passWord);
        findElement.FindElementWait(loginPage.userName,2);
        loginPage.userName.sendKeys(email);
        loginPage.passWord.sendKeys(passWord);
        loginPage.loginButton.click();
        //      Assert.assertEquals(userPage.user.getText(), "User");
        // System.out.println(userPage.user.getText().equals("User") ? "success change to user page" : "something wrong");
        if (email.equals("12@12.com")&passWord.equals("123456")) {
            findElement.FindElementWait(userPage.user,1);
            userPage.user.click();
            System.out.println("success change to user page");
            findElement.FindElementWait(userPage.logOut,1);
            userPage.logOut.click();
        } else  {
            System.out.println(loginPage.loginWrongMessage.getText().equals("Wrong username or password") ? "It is wrong input" : "something wrong");
        }

    }

    @AfterMethod
    public void afterMethod() {
//        WebElement we = findElement.FindElementWait(loginPage.loginWrongMessage, 2);
        //ppp.pageLoad("https://pinpineat.com/#!/login/");
    }


    @AfterTest
    public void AfterTest() {
        driver.close();
        driver.quit();
    }
}

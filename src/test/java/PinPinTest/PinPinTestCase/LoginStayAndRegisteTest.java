package PinPinTest.PinPinTestCase;

import PinPinTest.PageElements.LoginPage;
import PinPinTest.PageElements.UserPage;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginStayAndRegisteTest {
    WebDriver driver;
    PinPinTestPrepare ppp;
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
    @Parameters("browserName")
    public void beforeTest(String browserName) {
        ppp = new PinPinTestPrepare(browserName);
        driver = ppp.driver;
        String bName=System.getProperty("browserName");
        testAssert = new PinPinAssert(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        loginPage = new LoginPage(driver);
        userPage = new UserPage(driver);
        wait = new WebDriverWait(driver, 20);

    }

    @BeforeMethod
    public void BeforeMethod() {
        ppp.pageLoad("https://pinpineat.com/#!/login/");
    }

    @Test(enabled = true)
    public void testLoginFunction() {
        String email="12@12.com";
        String passWord="123456";
        System.out.println("---------------------------------------");
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: " + methodName + " with " + email + " , " + passWord);
        findElement.FindElementWait(loginPage.userName,2);
        loginPage.userName.sendKeys(email);
        loginPage.passWord.sendKeys(passWord);
        loginPage.getStayLogin.click();
        loginPage.loginButton.click();
        Assert.assertEquals(userPage.user.getText(), "User");
        System.out.println(userPage.user.getText().equals("User") ? "success change to user page" : "something wrong");

            findElement.FindElementWait(userPage.user,1);
            userPage.user.click();
            findElement.FindElementWait(userPage.userEmail,1);
            Assert.assertEquals(userPage.userEmail.getText(),email);
            findElement.FindElementWait(userPage.logOut,1);
            userPage.logOut.click();
}

    @AfterMethod
    public void afterMethod() {
        ppp.pageLoad("https://pinpineat.com/#!/login/");
    }


    @AfterTest
    public void AfterTest() {
//        driver.close();
        driver.quit();
    }
}

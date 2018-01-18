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
    PinPinTestPrepare ppp;
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    LoginPage loginPage;
    UserPage userPage;

    WebDriverWait wait;

    @DataProvider(name = "login")
    public Object[][] loginParameter() {
        Object[][] loginData = new Object[4][2];
        loginData[0][0] = "fdgrggggggggg@gffgfgfsfgh";
        loginData[0][1] = "dsfs fdsf";
        loginData[1][0] = "aaa@aaa.com";
        loginData[1][1] = "aaaa";
        loginData[2][0] = "qqqqqqqq";
        loginData[2][1] = "12333";
        loginData[3][0] = "中国";
        loginData[3][1] = "天空";

        return loginData;
    }

    @DataProvider(name = "loginNull")
    public Object[][] loginParameterNull() {
        Object[][] loginData = new Object[4][2];
        loginData[0][0] = "";
        loginData[0][1] = "";
        loginData[1][0] = "12@12.com";
        loginData[1][1] = "";
        loginData[2][0] = "";
        loginData[2][1] = "123456";
        loginData[3][0] = "                       ";
        loginData[3][1] = "123456";

        return loginData;
    }

    @BeforeTest
    @Parameters("browserName")
    public void beforeTest(String browserName) {
        ppp = new PinPinTestPrepare(browserName);
        driver = ppp.driver;
        String bName = System.getProperty("browserName");
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

    @Test(enabled = true, dataProvider = "login",priority = 0)
    public void testLoginFunction(String email, String passWord) {
        System.out.println("---------------------------------------");
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: " + methodName + " with " + email + " , " + passWord);

        loginInput(email,passWord );
        loginPage.loginButton.click();

        findElement.FindElementWait(loginPage.loginWrongMessage,2 );
        System.out.println(loginPage.loginWrongMessage.getText().equals("Wrong username or password") ? "It is wrong input" : "something wrong");
        Assert.assertEquals(loginPage.loginWrongMessage.getText(), "Wrong username or password");
    }

    @Test(enabled = true, dataProvider = "loginNull",priority = 1)
    public void testLoginFunctionNull(String email, String passWord) {
        System.out.println("---------------------------------------");
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: " + methodName + " with " + email + " , " + passWord);

        loginInput(email,passWord );
        loginPage.loginButton.click();

        if (!(loginPage.loginButton.getAttribute("disabled") == null)) {
            System.out.println("search disabled when search text is:" + email + " and " + passWord);

//            if (email.trim().equals(""))
//                Assert.assertEquals(loginPage.nullEmailMassage.getText(), "Username is required");
//
//            if (passWord.trim().equals(""))
//                Assert.assertEquals(loginPage.nullPassMassage.getText(), "Password is required");
        }
    }

    @Test(enabled = true,priority = 2)
    public void LoginTestPositive() {
        String userEmail = "12@12.com";
        String userPass = "123456";

        System.out.println("---------------------------------------");
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: " + methodName + " with " + userEmail + " , " + userPass);

        loginInput(userEmail,userPass );
        loginPage.loginButton.click();

        findElement.FindElementWait(userPage.user, 1);
        userPage.user.click();

        System.out.println(userPage.user.getText().equals("User") ? "success change to user page" : "something wrong");
        Assert.assertEquals(userPage.user.getText(), "User");
        findElement.FindElementWait(userPage.userEmail, 2);
        System.out.println("get email:" + userPage.userEmail.getText()+" test success");
        Assert.assertEquals(userPage.userEmail.getText(), "E-mail: "+userEmail);

        findElement.FindElementWait(userPage.logOut, 1);
        userPage.logOut.click();
    }
  @Test(enabled = true,priority = 3)
    public void LoginTestNegetive() {
        String userEmail = "'12@12.com'";
        String userPass = "'123456'";

        System.out.println("---------------------------------------");
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        System.out.println("current Test: " + methodName + " with " + userEmail + " , " + userPass);

        loginInput(userEmail,userPass );
        loginPage.loginButton.click();

      try {
          Thread.sleep(5000);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      System.out.println(loginPage.topLogin.getText().equals("Login") ? "Stay in login  page(test success)" : "something wrong");
        Assert.assertEquals(loginPage.topLogin.getText(), "Login");
    }

    @AfterMethod
    public void afterMethod() {
//        WebElement we = findElement.FindElementWait(loginPage.loginWrongMessage, 2);
        //ppp.pageLoad("https://pinpineat.com/#!/login/");
    }


    @AfterTest
    public void AfterTest() {
//        driver.close();
        driver.quit();
    }

    public void loginInput(String email,String passWord){
        findElement.FindElementWait(loginPage.userName, 2);
        loginPage.userName.sendKeys(email);
        loginPage.passWord.sendKeys(passWord);

    }
}

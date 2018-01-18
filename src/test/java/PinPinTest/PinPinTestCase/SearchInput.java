package PinPinTest.PinPinTestCase;

import PinPinTest.PageElements.HomePage;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SearchInput {
    WebDriver driver;
    PinPinTestPrepare ppp;
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    HomePage homePage;

    List<WebElement> resultName;
    List<WebElement> resultUrl;
    List<WebElement> resultMin;
    List<WebElement> resultRange;
    List<String> picUrl = new ArrayList<String>();

    WebElement result;
    String resultWindow;  //,restaurantWindow;
    int[] deliveryTime;
    WebDriverWait wait;
    Boolean disabledFlag;

    int maxTime = 0;
    int outOfRange = 0;
    int shopClosed = 0;

    @DataProvider(name = "searchString")
    public Iterator<Object[]> searchParameter() {
        String[] returnStr = {"guy concordia", "h2w 1x9", "h2w", "3895 St Laurent Blvd, Montreal, QC H2W 1X9",
                "", "asdf"};
        List<String> returnList = Arrays.asList(returnStr);
        List<Object[]> returnObj = new ArrayList<Object[]>();
        for (String s : returnList)
            returnObj.add(new Object[]{s});
        System.out.println("in data provider");
        return returnObj.iterator();
    }

    @BeforeTest
    @Parameters("browserName")
    public void setup(String browserName) {
        ppp = new PinPinTestPrepare(browserName);
        System.out.println("before test");
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/");
        testAssert = new PinPinAssert(driver);
        homePage = new HomePage(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        wait = new WebDriverWait(driver, 20);
    }

    @Test(dataProvider = "searchString")
    public void PinPinHomePageTest(String searchString) throws InterruptedException {
        String input = searchString;
        disabledFlag = false;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("   " + input);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Boolean pageLoad = testAssert.PageChangeAssert("Pinpin Eat");

        if (!pageLoad)
            System.out.println("can not load homePage");
        else {
            homePage.searchInput.sendKeys(input);
            //if search btn disabled prop is true means search btn is disabled, so bypass test.
            if (!(homePage.searchBtn.getAttribute("disabled") == null)) {
                System.out.println("search disabled when search text is:" + input);
                disabledFlag = true;
            }
            if (disabledFlag == false) {
                homePage.searchBtn.click();
            //assert search result page, assert if got right result for search string
                By CT = By.cssSelector("#delivertotext > span");
                WebElement centerText = findElement.FindElementWait(CT, 1);
                String getText = centerText.getText();
                String text = "Deliver to " + input + ".";

                if (text.equals(getText))
                    System.out.println("in right searchresult page");
                else
                    System.out.println("sth wrong, we are not in right page,center text is:" + getText);

                Assert.assertEquals(text, getText);
                resultElementCollect();

                resultWindow = driver.getWindowHandle();
                System.out.println("result window handle:"+resultWindow);
                resultProcess();
            }
        }
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("after method");
        outOfRange = 0;
        shopClosed = 0;
        maxTime = 0;
        if (disabledFlag == false)
            driver.navigate().back();
        else
            disabledFlag = false;
    }

    @AfterTest
    public void tearDown() {
        System.out.println("after test");
        try {
            ppp.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resultElementCollect() {
        //get all restaurant's name,url, delivery time,and distance.
        result = driver.findElement(By.id("shopscontainer"));
        resultName = result.findElements(By.className("sname"));
        resultUrl = result.findElements(By.className("shopImg"));
        resultMin = result.findElements(By.xpath("//div[@class='ng-scope']/div/div/a/div[2]/span[2]/b"));
        resultRange = result.findElements(By.xpath("//div[@class='shopName text-center']/span"));

    }

    public void resultProcess() {
        int min = 0;
        String outRange = null;
        for (int i = 0; i < resultUrl.size(); i++) {
//                  System.out.println("src:"+resultUrl.get(i).getAttribute("src"));
//                  System.out.println("ng-src:"+resultUrl.get(i).getAttribute("ng-src"));
            Assert.assertEquals(resultUrl.get(i).getAttribute("src"), resultUrl.get(i).getAttribute("ng-src"));
//get image url for future test assert
            picUrl.add(resultUrl.get(i).getAttribute("src"));
//get delivery time and find if  some restaurants out of range or currently closed
            outRange = resultRange.get(i).getText();
            if (outRange.contains("Out of range"))
                outOfRange++;
            if (outRange.contains("temporarilyClosed"))
                shopClosed++;
            if (resultMin.get(i).getText().length() > 2)
                min = Integer.parseInt(resultMin.get(i).findElement(By.xpath("//../../span[3]/b")).getText());
            else
                min = Integer.parseInt(resultMin.get(i).getText());

            if (maxTime < min)
                maxTime = min;
        }
        System.out.println("totally " + resultUrl.size() + " result" + ":max delivery time is" + maxTime);
        System.out.println("totally " + outOfRange + " restaurant out of range" + " and " + shopClosed + " restaurant currently closed");
    }

}


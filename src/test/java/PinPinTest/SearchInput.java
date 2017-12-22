package PinPinTest;

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
    PinPinTestPrepare ppp = new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    List<WebElement> resultName;
    // FindElementWait findElement;
    List<WebElement> resultUrl;
    List<WebElement> resultMin;
    List<WebElement> resultRange;

    List<String> picUrl = new ArrayList<String>();
    WebElement result;
    String resultWindow;  //,restaurantWindow;
    int[] deliveryTime;
    WebDriverWait wait;
    String serchInput = "guy concordia";
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
    public void setup() {
        System.out.println("before test");
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/");
        testAssert = new PinPinAssert(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        wait = new WebDriverWait(driver, 20);
    }

    @Test(dataProvider = "searchString")
    public void PinPinHomePageTest(String searchString) throws InterruptedException {
        String input = searchString;
        int min = 0;
        disabledFlag = false;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("   " + input);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Boolean pageLoad = testAssert.PageChangeAssert("Pinpin Eat");

        if (!pageLoad)
            System.out.println("can not load homePage");
        else {
            By mapSearchInput = By.id("mapsearchInput");
            By mapSearchBtn = By.id("mapsearchbtn");
            By loginBtn = By.linkText("Login");
            WebElement serchText = driver.findElement(mapSearchInput);
            WebElement serchBtn = driver.findElement(mapSearchBtn);
            WebElement login = driver.findElement(loginBtn);
            serchText.sendKeys(input);
//if search btn disabled prop is true then  search btn is disabled, so bypass test.
            if (!(serchBtn.getAttribute("disabled") == null)) {
                System.out.println("search disabled when search text is:" + input);
                disabledFlag = true;
            }

            if (disabledFlag == false) {
                serchBtn.click();
//assert search result page got right result for search string
                By CT = By.cssSelector("#delivertotext > span");
                WebElement centerText = findElement.FindElementWait(CT, 1);
                String getText = centerText.getText();
                String text = "Deliver to " + input + ".";
                Assert.assertEquals(text, getText);

                if (text.equals(getText))
                    System.out.println("in right searchresult page");
                else
                    System.out.println("sth wrong, we are not in right page,center text is:" + getText);

//get all restaurant's name, url, delivery time,and distance.
                result = driver.findElement(By.id("shopscontainer"));
                resultName = result.findElements(By.className("sname"));
                resultUrl = result.findElements(By.className("shopImg"));
                resultMin = result.findElements(By.xpath("//div[@class='ng-scope']/div/div/a/div[2]/span[2]/b"));
                resultRange = result.findElements(By.xpath("//div[@class='shopName text-center']/span"));

//recorder search result window handle
                resultWindow = driver.getWindowHandle();
                System.out.println(resultWindow);
//search result process
                String outRange = null;
                for (int i = 0; i < resultUrl.size(); i++) {
//                  System.out.println("src:"+resultUrl.get(i).getAttribute("src"));
//                  System.out.println("ng-src:"+resultUrl.get(i).getAttribute("ng-src"));
                    Assert.assertEquals(resultUrl.get(i).getAttribute("src"), resultUrl.get(i).getAttribute("ng-src"));
//get image url for future test assert
                    picUrl.add(resultUrl.get(i).getAttribute("src"));
//get delivery time and find if all restaurant out of range or currently closed
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

}

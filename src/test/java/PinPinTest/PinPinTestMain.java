package PinPinTest;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class PinPinTestMain {
    WebDriver driver;
    PinPinTestPrepare ppp= new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    List<WebElement> resultName;
    // FindElementWait findElement;
    List<WebElement> resultUrl;
    List<String>  picUrl
    String resultWindow;  //,restaurantWindow;

    @BeforeTest
    public void setup() {
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/" );
        testAssert=new PinPinAssert(driver);
        findElement=new FindElementWait(driver);
        switchwindow= new Switchwindow(driver);
    }

    @Test
    public void PinPinHomePageTest() throws InterruptedException {
        Boolean pageLoad=testAssert.PageChangeAssert("Pinpin Eat");
        if (!pageLoad)
            System.out.println("can not load homePage");
        else{
            By mapSearchInput=By.id("mapsearchInput");
            By mapSearchBtn=By.id("mapsearchbtn");
            By loginBtn=By.linkText("Login");
            WebElement serchText=driver.findElement(mapSearchInput);
            WebElement serchBtn=driver.findElement(mapSearchBtn);
            WebElement login=driver.findElement(loginBtn);

            serchText.sendKeys("guy concordia");
            serchBtn.click();

            //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#delivertotext > span")));
            By CT=By.cssSelector("#delivertotext > span");
            WebElement centerText = findElement.FindElementWait(CT, 1);

            String getText=centerText.getText();
            Assert.assertEquals("Deliver to guy concordia.",getText);
            if ("Deliver to guy concordia.".equals(getText))
                System.out.println("in right searchresult page");
            else
                System.out.println("sth wrong, we are not in right page,center text is:"+getText);

        }
    }


    @Test(dependsOnMethods ={"PinPinHomePageTest"} )
    public void getResult(){
        WebElement result= driver.findElement(By.id("shopscontainer"));
        resultName =result.findElements(By.className("sname"));
        resultUrl=result.findElements(By.className("shopImg"));
        resultWindow=driver.getWindowHandle();
        System.out.println(resultWindow);
        for (WebElement we:resultUrl) {
            Assert.assertEquals(we.getAttribute("src"),we.getAttribute("ng-src"));

        }
    }

    @Test(dependsOnMethods = {"getResult"})
    public void resultUrlTest() throws InterruptedException {
        WebElement img,dTime;

        //for (int i=0;i<resultUrl.size();i++){
           int i=0;
            System.out.println("---------------------------------------");
            System.out.print("try to click:"+resultName.get(i).getText());
            resultUrl.get(i).click();
            switchwindow.Switch(resultWindow);
            By imageBy=By.id("shopImage");
            img=findElement.FindElementWait(imageBy,2);
            System.out.println("img:  "+img.getAttribute("style"));
            System.out.println((resultUrl.get(i).getAttribute("src")));

        // Assert.assertEquals(img,resultUrl.get(i).getAttribute("src") );
            System.out.println("Now  -->"+"it changes to a right page");
            By topinfo=By.cssSelector("#topinfo > b:nth-child(8)");
            dTime=findElement.FindElementWait(topinfo,1);
            System.out.println("delivery time around:"+dTime.getText());
            System.out.println("restaurant window :"+driver.getWindowHandle());

    }

    @AfterTest
    public void tearDown(){
        try {
            ppp.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

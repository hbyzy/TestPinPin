package PinPinTest;

import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;
import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class serchInput {
    WebDriver driver;
    PinPinTestPrepare ppp= new PinPinTestPrepare();
    PinPinAssert testAssert;
    FindElementWait findElement;
    Switchwindow switchwindow;
    List<WebElement> resultName;
    // FindElementWait findElement;
    List<WebElement> resultUrl;
    List<String>  picUrl= new ArrayList<String>();
    String resultWindow;  //,restaurantWindow;
    int[] deliveryTime;
    WebDriverWait wait;
    String serchInput="guy concordia";

//    public PinPinSearchTest(String s){
//        this.serchInput=s;
//    }

    @DataProvider(name="searchString")
    public Iterator<Object[]> searchParameter(){
        String[] returnStr={"guy concordia","h2w 1x9","h2w","3895 St Laurent Blvd, Montreal, QC H2W 1X9",
                "","asdf"};
        List<String> returnList= Arrays.asList(returnStr);
        List<Object[]> returnObj=new ArrayList<Object[]>();
        for(String s:returnList)
            returnObj.add(new Object[]{s});
        System.out.println("in dataprovider");
        return returnObj.iterator();
    }

    @BeforeTest
    public void setup() {
        System.out.println("beforetest");
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/" );
        testAssert=new PinPinAssert(driver);
        findElement=new FindElementWait(driver);
        switchwindow= new Switchwindow(driver);
        wait = new WebDriverWait(driver, 20);
    }

    @Test(dataProvider = "searchString")
    //@Parameters("input")
    public void PinPinHomePageTest(String searchString) throws InterruptedException {
        String input=searchString;
        int min=0;
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("   "+input);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            serchText.sendKeys(input);

            if (serchBtn.getAttribute("translate disabled")==null){
                System.out.println("search disabled when search text is:"+input);
                return;
            }


            serchBtn.click();

            //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#delivertotext > span")));
            By CT=By.cssSelector("#delivertotext > span");
            WebElement centerText = findElement.FindElementWait(CT, 1);

            String getText=centerText.getText();
            String text="Deliver to "+input+".";
            Assert.assertEquals(text,getText);
            if (getText.equals(getText))
                System.out.println("in right searchresult page");
            else
                System.out.println("sth wrong, we are not in right page,center text is:"+getText);
        }

//get all restaurant's name url
        WebElement result= driver.findElement(By.id("shopscontainer"));
        resultName =result.findElements(By.className("sname"));
        resultUrl=result.findElements(By.className("shopImg"));
//recorder search result window handle
        resultWindow=driver.getWindowHandle();
        System.out.println(resultWindow);
//search result process
//        for (int i=0;i<resultUrl.size();i++){
//            System.out.println("src:"+resultUrl.get(i).getAttribute("src"));
//            System.out.println("ng-src:"+resultUrl.get(i).getAttribute("ng-src"));
//            Assert.assertEquals(resultUrl.get(i).getAttribute("src"),resultUrl.get(i).getAttribute("ng-src"));
//
//            picUrl.add(resultUrl.get(i).getAttribute("src"));
//
//           // wait.until(ExpectedConditions.elementToBeClickable(By.xpath("./div[2]/span[2]/b")));
//            min=Integer.parseInt(resultUrl.get(i).findElement(By.xpath("/a/div[2]/span[2]/b")).getText());
//            System.out.print(resultName.get(i).getText()+min);
//        }//*[@id="shopscontainer"]/div[3]/div[1]/div/div/a/div[2]/span[2]/b
    }////*[@id="shopscontainer"]/div[3]/div[1]/div/div/a
    ////*[@id="shopscontainer"]/div[3]/div[1]/div/div/a/div[1]/img
    //<b style="font-size: 13px;color:black" class="ng-binding">30</b>

    @AfterMethod
    public void afterMethod(){
        System.out.println("after method");
        driver.navigate().back();
    }
    @AfterTest
    public void tearDown(){
        System.out.println("after test");
        try {
            ppp.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

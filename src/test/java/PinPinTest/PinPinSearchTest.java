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

public class PinPinSearchTest {
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
    WebDriverWait  wait;
    String serchInput=null;

    @DataProvider(name="searchString")
    public Iterator<Object[]> searchParameter(){
       String[] returnStr={"guy concordia","h2w 1x9","h2w","3895 St Laurent Blvd, Montreal, QC H2W 1X9",
               "","asdf"};
       List<String> returnList= Arrays.asList(returnStr);
       List<Object[]> returnObj=new ArrayList<Object[]>();
       for(String s:returnList)
           returnObj.add(new Object[]{s});

        return returnObj.iterator();
    }
    @Factory(dataProvider="searchString")
    public void setparameter(String s){
        this.serchInput=s;
    }
    @BeforeTest
    public void setup() {
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/" );
        testAssert=new PinPinAssert(driver);
        findElement=new FindElementWait(driver);
        switchwindow= new Switchwindow(driver);
        wait = new WebDriverWait(driver, 20);
    }

    @Test(priority = 0)//dataProvider = "searchString")
    //@Parameters({"input"})
    public void PinPinHomePageTest() throws InterruptedException {
        String input=serchInput;
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
    }


    @Test(enabled=false,priority=1)//(dependsOnMethods ={"PinPinHomePageTest"} )
    public void getResult(){
        WebElement result= driver.findElement(By.id("shopscontainer"));
        resultName =result.findElements(By.className("sname"));
        resultUrl=result.findElements(By.className("shopImg"));
        resultWindow=driver.getWindowHandle();
        System.out.println(resultWindow);
        for (WebElement we:resultUrl) {
            Assert.assertEquals(we.getAttribute("src"),we.getAttribute("ng-src"));
            picUrl.add(we.getAttribute("src"));
        }
        deliveryTime=new int[resultUrl.size()];
    }

    @Test(enabled = false,priority=2)//(dependsOnMethods = {"getResult"})
    public void resultUrlTest() throws InterruptedException {
        Actions act=new Actions(driver);
        WebElement img,dTime;
        String imgurl,imgTemp,dlivTime;
        int minTime=0,maxTime=0;
        int index;
        for (int i=0;i<resultUrl.size();i++) {
            System.out.println("---------------------------------------");
            System.out.println("try to click:" + resultName.get(i).getText());

            wait.until(ExpectedConditions.elementToBeClickable(resultUrl.get(i)));
            act.moveToElement(resultUrl.get(i)).click().perform();
            switchwindow.Switch(resultWindow);// switch to restaurant window

            By imageBy = By.id("shopImage");
            do{
            img = findElement.FindElementWait(imageBy, 2);
            imgTemp = img.getAttribute("style");
            System.out.println("in loop"+imgTemp);}
            while(imgTemp.isEmpty());
            //imgurl = ppp.gotSubStr(imgTemp);
            //testAssert.strCompare(imgurl,picUrl.get(i));
            testAssert.regAssert(imgTemp,picUrl.get(i)) ;

            System.out.println("Now  -->" + "it changes to a right page");
            By topinfo = By.cssSelector("#topinfo > b:nth-child(8)");
            dTime = findElement.FindElementWait(topinfo, 1);
            System.out.println("delivery time around:" + dTime.getText());
            //index= dTime.getText().indexOf(" ");
            //deliveryTime[i]=Integer.parseInt(dTime.getText().substring(0,index));
            deliveryTime[i]=testAssert.regAssertStr("(\\d+)",dTime.getText());
            driver.close();
            driver.switchTo().window(resultWindow);
        }
        for(int i=0;i<deliveryTime.length;i++){
            if (deliveryTime[i]>maxTime)
                maxTime=deliveryTime[i];
            else if(deliveryTime[i]<minTime)
                    minTime=deliveryTime[i];
        }
        System.out.println("delivery time in range:"+minTime+"--"+maxTime+"   minutes");
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
//https://rationaleemotions.wordpress.com/2013/07/31/pretty-printing-with-testng/
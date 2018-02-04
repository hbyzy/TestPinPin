package PinPinTest;

import PinPinTest.PageElements.HomePage;
import PinPinTest.Prepare.PinPinTestPrepare;
import PinPinTest.Prepare.Switchwindow;

import PinPinTest.Tools.FindElementWait;
import PinPinTest.Tools.PinPinAssert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class PinPinSearchTest {
    WebDriver driver;
    PinPinTestPrepare ppp;
    PinPinAssert testAssert;
    HomePage homePage;
    FindElementWait findElement;
    Switchwindow switchwindow;
    List<WebElement> resultName;
    // FindElementWait findElement;
    List<WebElement> resultUrl;
    List<String> picUrl = new ArrayList<>();
    String resultWindow;  //,restaurantWindow;
    int[] deliveryTime;
    WebDriverWait wait;
    String searchInput = "guy concordia";

//// section for @factory
//        public PinPinSearchTest(String s) {
//        this.searchInput=s;
//    }

//    @DataProvider(name = "searchString")
//    //public Iterator<Object[]> searchParameter() {
//      public static Object[][] SearchParameter(){
    //      String[] returnStr = {"guy concordia", "h2w 1x9", "h2w", "3895 St Laurent Blvd, Montreal, QC H2W 1X9",
    //            "", "asdf"};
//        List<String> returnList = Arrays.asList(returnStr);
//        List<Object[]> returnObj = new ArrayList<Object[]>();
//
//        for (String s : returnList)
//            returnObj.add(new Object[]{s});
//        System.out.println("data provider");
//        return new Object[][]{{"guy concordia"}, {"h2w 1x9"}, {"h2w"}, {"3895St Laurent Blvd, Montreal"},{""},{"QC H2W 1X9"}};
    //        return returnObj.iterator();
//    }

//    @Factory(dataProvider = "searchString")
//    public  PinPinSearchTest( String param) {
//        this.searchInput = param;
//        System.out.println("searchInput is :"+this.searchInput);
//    }

    @BeforeTest
    //@Parameters("browserName")
    public void setup() {
        String browserName="chrome";
        ppp = new PinPinTestPrepare(browserName);
        driver = ppp.driver;
        ppp.pageLoad("https://www.pinpineat.com/");
        testAssert = new PinPinAssert(driver);
        homePage=new HomePage(driver);
        findElement = new FindElementWait(driver);
        switchwindow = new Switchwindow(driver);
        wait = new WebDriverWait(driver, 20);
        System.out.println("BeforeTest");
    }

    @Test//(priority = 0)
    public void PinPinHomePageTest() throws IOException {

        List<String> para = csvFile();

        for (int i = 1; i<para.size();i++){
            String input = para.get(i);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println("   " + input);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            homePage.searchInput.sendKeys(input);
            homePage.searchBtn.click();
            //wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#delivertotext > span")));
            By CT = By.cssSelector("#delivertotext > span");
            WebElement centerText = findElement.FindElementWait(CT, 1);

            String getText = centerText.getText();
            String text = "Deliver to " + input + ".";
            Assert.assertEquals(text, getText);
            if (getText.equals(text))
                System.out.println("in right searchresult page");
            else
                System.out.println("sth wrong, we are not in right page,center text is:" + getText);
//       }
            // collect all data for future test
            WebElement result = driver.findElement(By.id("shopscontainer"));
            resultName = result.findElements(By.className("sname"));
            resultUrl = result.findElements(By.className("shopImg"));
            resultWindow = driver.getWindowHandle();
            System.out.println(resultWindow);
            for (WebElement we : resultUrl) {
                Assert.assertEquals(we.getAttribute("src"), we.getAttribute("ng-src"));
                picUrl.add(we.getAttribute("src"));
            }
            deliveryTime = new int[resultUrl.size()];
            resultUrlTest();
            driver.get("https://www.pinpineat.com/");
        }
    }

    //@Test(enabled = true, dependsOnMethods = {"PinPinHomePageTest"})
    public void resultUrlTest() {
        Actions act = new Actions(driver);
        WebElement img, dTime;
        String imgurl, imgTemp, dlivTime;
        int minTime = 0, maxTime = 0;
        int index;
        for (int i = 0; i < resultName.size(); i++) {
            System.out.println("---------------------------------------");
            System.out.println("try to click:" + resultName.get(i).getText());

            wait.until(ExpectedConditions.elementToBeClickable(resultUrl.get(i)));
            act.moveToElement(resultUrl.get(i)).click().perform();
            switchwindow.Switch(resultWindow);// switch to restaurant window

            By imageBy = By.id("shopImage");
            do {
                img = findElement.FindElementWait(imageBy, 5);
                imgTemp = img.getAttribute("style");
                System.out.println("in loop" + imgTemp);
            }
            while (imgTemp.isEmpty());
            //imgurl = ppp.gotSubStr(imgTemp);
            //testAssert.strCompare(imgurl,picUrl.get(i));
            testAssert.regAssert(imgTemp, picUrl.get(i));

            System.out.println("Now  -->" + "it changes to a right page");
            By topinfo = By.cssSelector("#topinfo > b:nth-child(8)");
            dTime = findElement.FindElementWait(topinfo, 1);
            System.out.println("delivery time around:" + dTime.getText());
            //index= dTime.getText().indexOf(" ");
            //deliveryTime[i]=Integer.parseInt(dTime.getText().substring(0,index));
            deliveryTime[i] = testAssert.regAssertStr("(\\d+)", dTime.getText());
            driver.close();
            driver.switchTo().window(resultWindow);
        }
        for (int i = 0; i < deliveryTime.length; i++) {
            if (deliveryTime[i] > maxTime)
                maxTime = deliveryTime[i];
            else if (deliveryTime[i] < minTime)
                minTime = deliveryTime[i];
        }
        System.out.println("delivery time in range:" + minTime + "--" + maxTime + "   minutes");
    }

    @AfterTest
    public void tearDown() {
        try {
            ppp.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        PublicVar publicVar=new PublicVar();
//        publicVar.setFlag(true);
    }

    public List<String> csvFile()throws  IOException{
            String relativelyPath=System.getProperty("user.dir");
            System.out.println(relativelyPath);
            File file = new File(relativelyPath+"/src/test/resources/parameter.xlsx");
            FileInputStream fileInputStream = null;
            {
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            int nmberOfSheets = workbook.getNumberOfSheets();
            Sheet firstSheet=workbook.getSheetAt(0);

            // for (int i = 0; i < nmberOfSheets; i++) {
            //     Sheet firstSheet = workbook.getSheetAt(i);
            String sheetName= firstSheet.getSheetName();
            System.out.println(sheetName);
            Iterator<Row> iterator = firstSheet.rowIterator();
            List<String> para1= new ArrayList<String>();
            while (iterator.hasNext()) {

                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Cell nextCell = cellIterator.next();
                para1.add(nextCell.toString());
            }
            System.out.println(para1);
            return para1;
        }
}


//https://rationaleemotions.wordpress.com/2013/07/31/pretty-printing-with-testng/
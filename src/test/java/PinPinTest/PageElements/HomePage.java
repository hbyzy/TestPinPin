package PinPinTest.PageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;
    public By mapSerchInput = By.id("mapserchInput");
    public By mapSerchBtn = By.id("mapserchbtn");
    public By login = By.linkText("Login");

    @FindBy(xpath = "//*[@id=\"mapsearchInput\"]")
    public WebElement searchInput;
    @FindBy(xpath = "//*[@id=\"mapsearchbtn\"]")
    public WebElement searchBtn;
    @FindBy(xpath = "//*[@id='appstoreImg']")
    public WebElement appleStore;
    @FindBy(xpath = "//*[@id='playstoreImg']")
    public WebElement playStore;
    @FindBy(xpath = "//*[@id='body-content']/div/div/div[1]/div[1]/div/div[1]/div/div[2]/div[2]/div[2]/div/a[1]/span")
    public WebElement playStoreTitle;
    @FindBy(css = "#myNavBar > ul > li:nth-child(3) > a")
    public WebElement basket;
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[1]/a/span")
    public WebElement loginBtn;
    //translate
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[4]/a/span")
    public WebElement translate;
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[4]/ul/li[1]/a")
    public WebElement english;
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[4]/ul/li[2]/a")
    public WebElement french;
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[4]/ul/li[3]/a")
    public WebElement chinese;

    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[1]/a/span/span[2]")
    public WebElement loginText;

    //botttom click
    @FindBy(xpath = "//*[@id='footer']/div/div[2]/a[5]/p")
    public WebElement needDriver;
    @FindBy(xpath = "//*[@id='drivermodal']/div/div/div/p")
    public WebElement needDriverAlertText;
    @FindBy(xpath = "//*[@id='drivermodal']/div/div/div/button")
    public WebElement needDriverAlertBtn;


    public HomePage(WebDriver wdriver) {
        driver = wdriver;
        PageFactory.initElements(driver, this);
    }


}

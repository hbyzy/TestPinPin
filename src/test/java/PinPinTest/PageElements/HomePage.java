package PinPinTest.PageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    WebDriver driver;
    By mapSerchInput = By.id("mapserchInput");
    By mapSerchBtn = By.id("mapserchbtn");
    By login = By.linkText("Login");

    @FindBy(xpath = "//*[@id='appstoreImg']")
    public WebElement appleStore;
    @FindBy(xpath = "//*[@id='playstoreImg']")
    public WebElement playStore;
    @FindBy(css = "#myNavBar > ul > li:nth-child(3) > a")
    public WebElement basket;
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[1]/a/span")
    public WebElement loginBtn;
    @FindBy(xpath = "a[@class='dropdown-toggle")
    public WebElement translate;

    public HomePage(WebDriver wdriver) {
        driver = wdriver;
        PageFactory.initElements(driver,this);
    }


}

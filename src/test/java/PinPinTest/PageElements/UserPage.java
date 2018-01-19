package PinPinTest.PageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UserPage {
    WebDriver driver;


    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[2]/a/span/span[2]")
    public WebElement user;
    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[2]")
    public WebElement userClass;
    @FindBy(xpath = "//*[@id='view']/div/div[2]/div/button")
    public WebElement logOut;
    @FindBy(xpath = "//*[@id='view']/div/div[2]/div/h3")
    public WebElement userEmail;


    public UserPage(WebDriver wdriver) {
        driver = wdriver;
        PageFactory.initElements(driver, this);

    }
}

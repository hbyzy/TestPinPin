package PinPinTest.PageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class pageElements {
    WebDriver driver;
    By mapSerchInput=By.id("mapserchInput");
    By mapSerchBtn=By.id("mapserchbtn");
    By login=By.linkText("Login");

    @FindBy (xpath="//*[@id='appstoreImg']")
    WebElement appleStore;
    @FindBy (xpath="//*[@id='playstoreImg']")
    WebElement  playStore;
    @FindBy (id="cartbadge")
    WebElement longinPic;
    @FindBy (xpath="li[@class='userBtn")
    WebElement loginBtn;
    @FindBy (xpath="a[@class='dropdown-toggle")
    WebElement translate;

    public pageElements(WebDriver wdriver){
        driver=wdriver;
    }




}

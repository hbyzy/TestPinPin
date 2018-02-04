package PinPinTest.PageElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    public By LoginTxt = By.xpath("//*[@id='loginDiv']/div[2]/div/h4");


    @FindBy(xpath = "//*[@id='myNavBar']/ul/li[1]/a/span/span[2]")
    public WebElement topLogin;
    @FindBy(xpath = "//*[@id='username']")
    public WebElement userName;
    @FindBy(xpath = "//*[@id='password']")
    public WebElement passWord;
    @FindBy(xpath = "//*[@id='loginDiv']/div[2]/div/form/div[3]/div/label/p")
    public WebElement getStayLogin;
    @FindBy(xpath = "//*[@id='loginDiv']/div[2]/div/form/div[3]/button")
    public WebElement loginButton;
    @FindBy(xpath = "//*[@id='loginDiv']/div[2]/div/form/div[3]/p[1]/a")
    public WebElement forgotPassword;
    @FindBy(xpath = "//*[@id='loginDiv']/div[2]/div/form/div[3]/p[2]/a")
    public WebElement signUp;
    @FindBy(xpath = "//*[@id='loginDiv']/div[2]/div/p")
    public WebElement loginWrongMessage;
    @FindBy(xpath = "//*[@id=\"loginDiv\"]/div[2]/div/form/div[1]/span")
    public WebElement nullEmailMassage;
    @FindBy(xpath = "//*[@id=\"loginDiv\"]/div[2]/div/form/div[2]/span")
    public WebElement nullPassMassage;



    public LoginPage(WebDriver wdriver) {
        driver = wdriver;
        PageFactory.initElements(driver, this);

    }

}

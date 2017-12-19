package PinPinTest.Prepare;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class Switchwindow {
    WebDriver driver;
    public Switchwindow(WebDriver wdriver){
        driver=wdriver;
    }
    public void Switch(String handle){
        Set<String> wHandles= driver.getWindowHandles();
        for(String s: wHandles){
           // System.out.println(s);
            if (!s.equals(handle)){
                driver.switchTo().window(s);
                return;
            }
        }
    }
}

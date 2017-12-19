package PinPinTest.Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindElementWait {
    WebDriver driver;
    WebDriverWait  wait;
    WebElement element;
    public FindElementWait(WebDriver wdriver){
        driver=wdriver;
    }

    public WebElement FindElementWait(By byStr,int i) {
        wait = new WebDriverWait(driver, 20);

        switch (i) {
            case 1:
                wait.until(ExpectedConditions.elementToBeClickable(byStr));
                element = driver.findElement(byStr);
                break;
            case 2:
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(byStr)));
                element = driver.findElement(byStr);
                break;

        }
        return element;
    }

}


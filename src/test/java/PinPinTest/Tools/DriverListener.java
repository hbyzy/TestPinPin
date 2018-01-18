package PinPinTest.Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class DriverListener implements WebDriverEventListener {
    @Override
    public void beforeAlertAccept(WebDriver webDriver) {
        //System.out.println("here is beforeAlertAccept");
    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {
        //System.out.println("here is afterAlertAccept");
    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {
        //System.out.println("here is afterAlertDismiss");
    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {
        //System.out.println("here is beforeAlertDismiss");
    }

    @Override
    public void beforeNavigateTo(String s, WebDriver webDriver) {
       // System.out.println("here is before navigate to listener"+s);
    }

    @Override
    public void afterNavigateTo(String s, WebDriver webDriver) {
        //System.out.println("here is after navigate to listener");
    }

    @Override
    public void beforeNavigateBack(WebDriver webDriver) {
        //System.out.println("here is before navigate back listener");
    }

    @Override
    public void afterNavigateBack(WebDriver webDriver) {
        //System.out.println("here is after navigate back listener");
    }

    @Override
    public void beforeNavigateForward(WebDriver webDriver) {
        //System.out.println("here is before navigate forward listener");
    }

    @Override
    public void afterNavigateForward(WebDriver webDriver) {
        //System.out.println("here is after navigate forward listener");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
        //System.out.println("here is before navigate refresh listener");
    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
        //System.out.println("here is after navigate refresh listener");
    }

    @Override
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        //System.out.println("here is before findby listener");
    }

    @Override
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        //System.out.println("here is after findby listener");
    }

    @Override
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        //System.out.println("here is before clickon listener");
    }

    @Override
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        //System.out.println("here is after clickon listener");
    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        //System.out.println("here is before change valueof listener");
    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        //System.out.println("here is after change value of listener");
    }

    @Override
    public void beforeScript(String s, WebDriver webDriver) {
        //System.out.println("here is before script listener");
    }

    @Override
    public void afterScript(String s, WebDriver webDriver) {
        //System.out.println("here is after script listener");
    }

    @Override
    public void onException(Throwable throwable, WebDriver webDriver) {
        //System.out.println("here is on exception listener");
    }
}

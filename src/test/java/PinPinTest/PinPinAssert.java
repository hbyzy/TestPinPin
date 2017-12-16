package PinPinTest;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class PinPinAssert {
    WebDriver driver;
    public int timeOut = 5000;
    public int interval = 500;

    public PinPinAssert(WebDriver wdriver) {
        this.driver = wdriver;
    }

    public boolean PageChangeAssert(String rname) throws InterruptedException {
        String currentHandle;
        Set<String> handles;
        while (timeOut > 0) {
            String Title = driver.getTitle();
            if (rname.equals(Title)) {
                System.out.println("chang to right page:" + Title);
                return true;
            } else {
                currentHandle = driver.getWindowHandle();
                handles = driver.getWindowHandles();
                if (handles.size() > 1) {
                    for (String e : handles) {
                        if (!e.equals(currentHandle)) {
                            driver.switchTo().window(e);
                        }
                    }
                } else {
                    Thread.sleep(interval);
                    timeOut -= interval;
                }
            }
        }

        System.out.println("can not chage to right page:" + rname);
        return false;
    }
}

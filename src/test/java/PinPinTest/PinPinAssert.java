package PinPinTest;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void strCompare(String str1, String str2) {
        String sub1, sub2;
        sub1 = str1.substring(0, str1.indexOf(' '));
        sub2 = str2.substring(0, str1.indexOf(' '));
        Assert.assertEquals(sub1, sub2);
    }


    public void regAssert(String str1, String str2) {

        String regex = "(\\bshop\\d+)";
        System.out.println(str1);
        System.out.println(str2);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str1);
        Matcher matcher1 = pattern.matcher(str2);

        while (matcher.find()&matcher1.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
                System.out.println("Group " + i + ": " + matcher1.group(i));
            }
        }
        //Assert.assertEquals(matcher.group(0), matcher1.group(0));

    }
}



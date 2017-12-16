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

    public void strCompare(String str1,String str2){
        String sub1,sub2;
        sub1=str1.substring(0,str1.indexOf(' '));
        sub2=str2.substring(0,str1.indexOf(' '));
        Assert.assertEquals(sub1,sub2);
    }

    public void  regAssert(String str1,String str2) {
        String re1 = "\\bshop\\d+";
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(str1);
        Matcher n = p.matcher(str2);
        System.out.println(m.group());
        System.out.println(n.group());

        Assert.assertEquals(m.group(),(n.group()));

    }
}

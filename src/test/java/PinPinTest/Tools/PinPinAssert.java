package PinPinTest.Tools;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
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




    public void regAssert(String str1, String str2) {
        List<String> res1 = new ArrayList<>();
        List<String> res2 = new ArrayList<>();

        String regex = "(\\bshop\\d+)";
        System.out.println("str1:  "+str1);
        System.out.println("str2:  "+str2);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str1);
        Matcher matcher1 = pattern.matcher(str2);
        matcher.matches();
        matcher1.matches();

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                res1.add(matcher.group(i));
                System.out.println("str1  Group " + i + ": " + res1.get(i-1));
            }
        }

        while (matcher1.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                res2.add(matcher1.group(i));
                System.out.println("str2 Group " + i + ": " + res2.get(i-1));
            }
        }
            System.out.println("str1:"+res1.get(0));
            System.out.println("str2:"+res2.get(0));
        //Assert.assertEquals(res1.get(0), res2.get(0));

    }

    public int regAssertStr(String reg, String str) {
        String regex = reg, returnStr = null;
        System.out.println(reg + ":" + str);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            returnStr = matcher.group();

//                System.out.println("Group " + 1 + ": " + matcher.group(1));
     //     System.out.println("reg result:" + returnStr);
        }
        return Integer.parseInt(returnStr);
    }
}



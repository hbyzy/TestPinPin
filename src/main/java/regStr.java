import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regStr {
    public static void main(String args[]) {
        final String regex = "(\\bshop\\d+)";
        final String string = "background-image: url(\"https://pinpinmarket.s3.amazonaws.com/i/shop47_%E5%8C%97%E6%96%B9%E4%BA%BA%E5%AE%B6storecover.png\")";


        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            System.out.println("fully match " + matcher.group(0));
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }

            //Assert.assertEquals(m.group(),(n.group()));

        }
    }

}

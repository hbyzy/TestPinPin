package yahooApi;

import cucumber.api.java.en.Given;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;

public class YahooRest {
    public static String host;
    public static String para;
    @BeforeTest
    public void setup() throws IOException {
    Properties prop=getProperties();
    host = prop.getProperty("host.url");
    para= prop.getProperty("host.para");
    System.out.println(host);
    }
    @Test(enabled = false)
    public void testJson() throws UnsupportedEncodingException {
        ValidatableResponse response = given().get(host+"{search}&format={format}",para,"json").then();
        response.statusCode(200);
        response.body("status", equalTo(null));
        response.assertThat().body("query.results.channel.location.country",equalTo("Canada") ).body("query.results.channel.location.city",equalTo("Montreal"));
        Response useResponse=get(host+"{search}&format={format}",para,"json");
        List<Object> temp=from(useResponse.asString()).getList("query.results.channel.item.forecast");
//        List<Object> aa = from(response.toString()).getList("results[0].address_components.short_name");
        for(int i=0;i<temp.size();i++)
            System.out.println(temp);
    }
    @Test()
    public void testXml(){
        ValidatableResponse response = given().get(host+"{search}",para).then();
        response.statusCode(200);
//        response.body("status", equalTo(null)); <yweather:location city="Montreal" country="Canada"
        response.assertThat().body("query.results.channel.location.@country",equalTo("Canada") );
    }
    public static Properties getProperties() throws IOException {
        String resourceName="config.properties";
        ClassLoader loader=Thread.currentThread().getContextClassLoader();
        Properties prop=new Properties();
        InputStream resourceStram=loader.getResourceAsStream(resourceName);
        prop.load(resourceStram);
        return prop;
    }
}

package yahooApi;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class YahooApi {
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParserConfigurationException, InstantiationException, SAXException, IllegalAccessException {
        YahooResponse yahooResponse = new YahooResponse();
        yahooResponse.apiChoice("xml");
    }
}

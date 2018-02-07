package yahooApi;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class YahooApi {
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParserConfigurationException, InstantiationException, SAXException, IllegalAccessException, DocumentException {
        YahooResponse yahooResponse = new YahooResponse();
        yahooResponse.apiChoice("xml");
//        yahooResponse.apiChoice("json");
    }
}

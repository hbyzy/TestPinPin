package yahooApi;

import cucumber.api.java.cs.A;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class YahooResponse {
    static public Map<String,String> result=new HashMap<>();
    public void apiChoice(String choice) throws IOException, ClassNotFoundException, ParserConfigurationException, InstantiationException, SAXException, IllegalAccessException, DocumentException {
        if (choice.equals("json")) {
            String YQL = " from weather.forecast where woeid in (select woeid from geo.places(1) where text='montreal,qc') and u='c'";

            String url = "";

            {
                try {
                    url = "https://query.yahooapis.com/v1/public/yql?q=select+%2A" + URLEncoder.encode(YQL, "UTF-8") + "&format=json";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(url);
            yahooJson(url);
        } else if (choice.equals("xml")) {
            String YQL = " from weather.forecast where woeid in (select woeid from geo.places(1) where text='montreal,qc') and u='c'";

            String url = "";

            {
                try {
                    url = "https://query.yahooapis.com/v1/public/yql?q=select+%2A" + URLEncoder.encode(YQL, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(url);
            yahooXml(url);

        }
    }

    public void yahooJson(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = httpClient.execute(httpPost);

        HttpEntity myEntity = response.getEntity();
        System.out.println(myEntity.getContentType());
        System.out.println(myEntity.getContentLength());

        String resString = EntityUtils.toString(myEntity);
        JSONObject obj = new JSONObject(resString);
        System.out.println(obj.toString(4));
        jsonpaser(obj);

        JSONObject query = obj.getJSONObject("query");
        JSONObject result = query.getJSONObject("results");
        JSONObject channel = result.getJSONObject("channel");
        JSONObject items = channel.getJSONObject("item");
        JSONArray forecast = items.getJSONArray("forecast");
        System.out.println("forecast:" + forecast);
        for (int i = 0; i < forecast.length(); i++) {
            System.out.println(forecast.get(i));
            JSONObject value = (JSONObject) forecast.get(i);
            Iterator it = value.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object valuePr = value.get(key);
                System.out.println(key + "=" + valuePr);
            }
        }

    }

    public Object jsonpaser(Object json) {

        if (json instanceof JSONObject) {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonStr = (JSONObject) json;
            Iterator it = jsonStr.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = jsonStr.get(key);
                jsonObject.put(key, jsonpaser(value));
                System.out.println("object: " + "key=" + key + "  value=" + value);

            }
            System.out.println("object return:" + jsonObject);
            return jsonObject;
        } else if (json instanceof JSONArray) {
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonStr = (JSONArray) json;
            for (int i = 0; i < jsonStr.length(); i++) {
                jsonArray.put(jsonpaser(jsonStr.get(i)));
                System.out.println("array: " + "index=" + i + "  value=" + jsonStr.get(i));
            }
            System.out.println("array return:" + jsonArray);
            return jsonArray;
        } else return json;
    }

    public void yahooXml(String url) throws IOException, ClassNotFoundException, ParserConfigurationException, InstantiationException, SAXException, IllegalAccessException, DocumentException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = httpClient.execute(httpPost);

        HttpEntity myEntity = response.getEntity();
        System.out.println(myEntity.getContentType());
        System.out.println(myEntity.getContentLength());

        String resString = EntityUtils.toString(myEntity);
        System.out.println(resString);
        prettyPrintXML(resString);
//dom4j
        Document document = DocumentHelper.parseText(resString);
        Element root = document.getRootElement();
        String rootname = root.getName();
        String rootText = root.getText();
        System.out.println(rootname + "  " + rootText);
        listNodes(root);

        for (String key : result.keySet()) {
            System.out.println(key+"="+result.get(key));
//        List list=document.selectNodes("//item/@yweather:forecast");
//        System.out.println(list);
        }
    }

    //    not the best result for  pretty printing xml
//    public void paserXMl(String resSting) throws TransformerException {
//        Source xmlInput=new StreamSource(new StringReader(resSting));
//        StringWriter stringWriter= new StringWriter();
//        StreamResult xmlOutput= new StreamResult(stringWriter);
//        TransformerFactory tFormFactory=TransformerFactory.newInstance();
//        tFormFactory.setAttribute("indent-number",2);
//
//        Transformer tf=tFormFactory.newTransformer();
//        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        tf.setOutputProperty(OutputKeys.INDENT,"yes");
//        tf.transform(xmlInput,xmlOutput);

    //        System.out.println(xmlOutput.getWriter().toString());
//
//    }
    public void prettyPrintXML(String resString) throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        InputSource src = new InputSource(new StringReader(resString));
        Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
        Boolean keepDeclaration = Boolean.valueOf(resString.startsWith("<?xml"));
        System.setProperty(DOMImplementationRegistry.PROPERTY, "com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");

        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("ls");
        LSSerializer writer = impl.createLSSerializer();

        writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
        writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);
        System.out.println(writer.writeToString(document));
    }

    public void listNodes(Element node) {

//        List<Attribute> list = node.attributes();
//        for (Attribute attribute : list) {
//            System.out.println("Attribute:" + attribute.getName() + ">text>" + attribute.getText());
//        }
//            if (!node.getTextTrim().equals("")) {
//                System.out.println(node.getName() + ":" + node.getText());
//            }
        Iterator<Element> iterator = node.elementIterator();
        System.out.println("begin parse---------------------");
        while (iterator.hasNext()) {
            Element e = iterator.next();
            System.out.println(e);
            Iterator iterator1=e.elementIterator();
            while(iterator1.hasNext()){
                Element eSub= (Element) iterator1.next();
                System.out.println("subelement:>>"+eSub.getName()+"Text: "+eSub.getText()+"attrutes: "+eSub.attributes());

                if (eSub.getName().equals("forecast")){
                    String code= eSub.attributeValue("code");
                    String date=eSub.attributeValue("date");
                    String day=eSub.attributeValue("day");
                    String high= eSub.attributeValue("high");
                    String low=eSub.attributeValue("low");
                    String text=eSub.attributeValue("text");

                    result.put(code+":date=",date);
                    result.put(code+":day=",day);
                    result.put(code+":high=",high);
                    result.put(code+":low=",low);
                    result.put(code+":text=",text);
                      }
                    System.out.println("try got result:"+result);
                }

            listNodes(e);
        }
    }


}




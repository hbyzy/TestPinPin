package yahooApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.util.UrlEncoded;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class YahooApi {
    public static void main(String[] args) throws IOException {
        String YQL = " from weather.forecast where woeid in (select woeid from geo.places(1) where text='montreal,qc') and u='c'";

        String url="";

        {
            try {
                url = "https://query.yahooapis.com/v1/public/yql?q=select+%2A"+URLEncoder.encode(YQL,"UTF-8")+"&format=json";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        System.out.println(url);
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpPost httpPost=new HttpPost(url);
        CloseableHttpResponse response=httpClient.execute(httpPost);

        HttpEntity myEntity = response.getEntity();
        System.out.println(myEntity.getContentType());
        System.out.println(myEntity.getContentLength());

        String resString = EntityUtils.toString(myEntity);
        JSONObject obj = new JSONObject(resString);
        System.out.println(obj.toString(4));
//        System.out.println(jsonpaser(obj));

        JSONObject query=obj.getJSONObject("query");
        JSONObject result=query.getJSONObject("results");
        JSONObject channel=result.getJSONObject("channel");
        JSONObject items=channel.getJSONObject("item");
        JSONArray  forecast=items.getJSONArray("forecast");
        System.out.println("forecast:"+forecast);
        for(int i=0;i<forecast.length();i++) {
            System.out.println(forecast.get(i));
            JSONObject value=(JSONObject)forecast.get(i);
            Iterator it=value.keys();
            while(it.hasNext()){
                String key= (String)it.next();
                Object valuePr=value.get(key);
                System.out.println(key+"="+valuePr);
            }
        }
    }
    public static Object jsonpaser(Object json){

        if (json instanceof JSONObject){
            JSONObject jsonObject=new JSONObject();
            JSONObject jsonStr=(JSONObject)json;
            Iterator it= jsonStr.keys();
            while(it.hasNext()){
                String key=(String) it.next();
                Object value=jsonStr.get(key);
                jsonObject.put(key,jsonpaser(value));
                System.out.println("object: "+"key="+key+"  value="+value);

            }System.out.println("object return:"+jsonObject);
            return jsonObject;
        }else if(json instanceof JSONArray){
            JSONArray jsonArray=new JSONArray();
            JSONArray jsonStr=(JSONArray)json;
            for (int i=0;i<jsonStr.length();i++){
                jsonArray.put(jsonpaser(jsonStr.get(i)));
                System.out.println("array: "+"index="+i+"  value="+jsonStr.get(i));
            }System.out.println("array return:"+jsonArray);
            return jsonArray;
        }else return json;
    }
    public void jsonSearch(Object json){




    }
}

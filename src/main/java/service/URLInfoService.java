package service;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-11
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class URLInfoService {

    public static URLInfoService getURLInfoServiceInstance(){
        if(service == null){
            service = new URLInfoService();
        }
        return service;
    }

    public String getXmlSringByDistrictId(String districtId){
        String districtUrl = FLASHHEADER + districtId + ".xml";
        return fetchInfoFromUrl(districtUrl);
    }

    public String fetchHtmlJsonStringById(String districtId){
        String districtUrl = WEATHERHEADER + districtId + ".html";

        return fetchInfoFromUrl(districtUrl);

    }

    public String fetchHtmlStringById(String districtId){
        String districtUrl = URLHEADER + districtId + ".shtml";

        return fetchInfoFromUrl(districtUrl);
    }

    public String fetchInfoFromUrl(String urlName){
        StringBuilder sbr = new StringBuilder();
        try {
            URL url = new URL(urlName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null){
                sbr.append(lineStr);
            }
        } catch (MalformedURLException e) {
            fetchInfoFromUrl(urlName);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sbr.toString();
    }
    private URLInfoService(){

    }

    private byte[] contentBytes  = new byte[1024000*8];

    private final int MAXSIZE = 8*1024000;

    public static URLInfoService service;


    private static final String URLHEADER = "http://www.weather.com.cn/weather/";
    private static final String WEATHERHEADER = "http://www.weather.com.cn/data/sk/";
    private static final String FLASHHEADER = "http://flash.weather.com.cn/sk2/";
}

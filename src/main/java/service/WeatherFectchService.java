package service;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:17
 * To change this template use File | Settings | File Templates.
 */
public class WeatherFectchService {

    public static WeatherFectchService getServiceInstance(){
        if(service == null){
            service = new WeatherFectchService();
        }

        return service;
    }

    public Map<String,String> encodingCountryProvinces(String countryName){
        String jsonString = getCountryProvincesJsonString(countryName);

        return encodingJson2Map(jsonString);
    }

    public Map<String,String> encodingProvinceCities(String provinceNo){
        String jsonString = getProvinceCitiesJsonString(provinceNo);

        return encodingJson2Map(jsonString);
    }

    public Map<String,String> encodingCityDistrict(String cityNo){
        String jsonString = getCityDistrictJsonString(cityNo);

        return encodingJson2Map(jsonString);
    }


    private WeatherFectchService(){
        //To-DO: nothing special, maybe in future.
    }

    private String getCountryProvincesJsonString(String countryName){

        String countryUrlString = COUNTRYHEADER + countryName + ".html";

        return urlInfoService.fetchInfoFromUrl(countryUrlString);
    }

    private String getProvinceCitiesJsonString(String provinceNo){
        String urlProvinceStr = PROVINCEHEADER + provinceNo + ".html";

        return urlInfoService.fetchInfoFromUrl(urlProvinceStr);
    }

    private String getCityDistrictJsonString(String cityNo){
        String urlCityStr = CITYHEADER + cityNo + ".html";

        return urlInfoService.fetchInfoFromUrl(urlCityStr);
    }

    private Map<String,String> encodingJson2Map(String jsonStr){
        Map<String,String> provinceMap = new HashMap<String,String>();
        Gson gson = new Gson();
        provinceMap = (Map<String,String>)gson.fromJson(jsonStr,provinceMap.getClass());

        return (Map<String,String>)provinceMap;
    }

    public final static String COUNTRYHEADER = "http://www.weather.com.cn/data/city3jdata/";
    public final static String PROVINCEHEADER = "http://www.weather.com.cn/data/city3jdata/provshi/";
    public final static String CITYHEADER = "http://www.weather.com.cn/data/city3jdata/station/";


    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();


    private static WeatherFectchService service;

}

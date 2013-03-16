package app;

import models.dbmodels.WeatherInfoBean;
import models.locationInfo.CityDistrictBean;
import models.locationInfo.CountryBean;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import service.DistrictWeatherInfoService;
import service.LoadCountryProvincesService;
import service.SessionFactoryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-17
 * Time: 下午7:34
 * To change this template use File | Settings | File Templates.
 */
public class FetchCityDistrictWeatherApp {

    public static void main(String[] args){

    }


    public void reloadCountryBean(String countryName){
//        countryBean = loadCountryProvincesService.InitCountryInfomation(countryName);
    }

    public void startFetchWeatherInfo(){
        Timer timer = new Timer();
        Timer emptyMapTimer = new Timer();
        emptyMapTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               latestWeatherInfoMap.clear();
            }

        },0L,hourMills);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<CityDistrictBean> cityDistrictBeanList = countryBean.getCityDistrictList();
                for(CityDistrictBean cityDistrictBean : cityDistrictBeanList){
                    String districtNo = cityDistrictBean.getDistrictNo();
                    WeatherInfoBean currentWeatherInfoBean = loadCurrentInTimeWeatherRecord(cityDistrictBean);
                    WeatherInfoBean lastestWeatherInfo = latestWeatherInfoMap.get(districtNo);

                    Session session = sessionFactory.openSession();
                    if(lastestWeatherInfo != null){
                        if(!lastestWeatherInfo.equals(currentWeatherInfoBean)){
                            latestWeatherInfoMap.put(districtNo,currentWeatherInfoBean);
                            session.beginTransaction();
                            session.save(currentWeatherInfoBean);
                            session.getTransaction().commit();
                        }
                    } else {
                        latestWeatherInfoMap.put(districtNo,currentWeatherInfoBean);
                    }
                }
            }
        },0,timeGap);
    }



    private WeatherInfoBean loadCurrentInTimeWeatherRecord(CityDistrictBean cityDistrictBean){

        return districtWeatherInfoService.getDistrictWeatherInfo(cityDistrictBean);

    }
    private long timeGap = 20*60*1000;   //Five minutes.
    private long hourMills = 60 * 60 * 1000;
    private Map<String,WeatherInfoBean> latestWeatherInfoMap = new HashMap<String, WeatherInfoBean>();
    private DistrictWeatherInfoService districtWeatherInfoService = DistrictWeatherInfoService.getDistrictWeatherInfoServiceInstance();
    private SessionFactory sessionFactory = SessionFactoryService.getSessionFactory();

    private static final String DEFAULTCOUNTRY = "china";
    private static LoadCountryProvincesService loadCountryProvincesService = LoadCountryProvincesService.getLoadCountryProvinceServiceInstance();

    private static CountryBean countryBean;
    static {
//        countryBean = loadCountryProvincesService.InitCountryInfomation(DEFAULTCOUNTRY);
    }
}

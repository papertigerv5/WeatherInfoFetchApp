package app;

import models.dbmodels.WeatherInfoBean;
import models.locationInfo.CityDistrictBean;
import models.locationInfo.CountryBean;
import service.DistrictWeatherInfoService;
import service.LoadCountryProvincesService;

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
        FetchCityDistrictWeatherApp app = new FetchCityDistrictWeatherApp();
        CityDistrictBean peking = countryBean.getProvinceBeanList().get(21).getCities().get(0).getDistrictBeanList().get(1);

        app.startFetchWeatherInfo();
    }


    public void reloadCountryBean(String countryName){
        countryBean = loadCountryProvincesService.InitCountryInfomation(countryName);
    }

    public void startFetchWeatherInfo(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<CityDistrictBean> cityDistrictBeanList = countryBean.getCityDistrictList();
                for(CityDistrictBean cityDistrictBean : cityDistrictBeanList){
                    String districtNo = cityDistrictBean.getDistrictNo();
                    WeatherInfoBean currentWeatherInfoBean = loadCurrentInTimeWeatherRecord(cityDistrictBean);
                    WeatherInfoBean lastestWeatherInfo = latestWeatherInfoMap.get(districtNo);
                    if(lastestWeatherInfo != null){
                        if(!lastestWeatherInfo.equals(currentWeatherInfoBean)){
                            latestWeatherInfoMap.put(districtNo,currentWeatherInfoBean);
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
    private long timeGap = 5*60*1000;   //Five minutes.
    private Map<String,WeatherInfoBean> latestWeatherInfoMap = new HashMap<String, WeatherInfoBean>();
    private DistrictWeatherInfoService districtWeatherInfoService = DistrictWeatherInfoService.getDistrictWeatherInfoServiceInstance();

    private static final String DEFAULTCOUNTRY = "china";
    private static LoadCountryProvincesService loadCountryProvincesService = LoadCountryProvincesService.getLoadCountryProvinceServiceInstance();
    private static CountryBean countryBean;
    static {
        countryBean = loadCountryProvincesService.InitCountryInfomation(DEFAULTCOUNTRY);
    }
}

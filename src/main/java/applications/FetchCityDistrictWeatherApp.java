package applications;

import models.locationInfo.CountryBean;
import models.websitebeans.WebSiteBean;
import service.FetchCityDistrictWeatherInfoService;
import service.LoadCountryProvincesService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        app.startFetchWeatherInfo();
    }

    public static FetchCityDistrictWeatherApp getFetchCityDistrictWeatherAppInstance(){
        if(fetchCityDistrictWeatherApp == null){
            fetchCityDistrictWeatherApp = new FetchCityDistrictWeatherApp();
        }

        return fetchCityDistrictWeatherApp;
    }

    public void reloadCountryInfo(){
        fetchCityDistrictWeatherInfoService.reloadCountryInfo(LoadCountryProvincesService.DEFAULTCOUNTRY);
    }

    public void startFetchWeatherInfo(){
        fetchCityDistrictWeatherInfoService.startFetchCityDistrictScheduledActions();
    }

    /**
     * Construct the instance, try to reload the country info bean while there is nothing in Citydistrict table.
     */
    private FetchCityDistrictWeatherApp(){
    }

    private CountryBean countryBean;
    private BlockingQueue<WebSiteBean> productString = new LinkedBlockingQueue<WebSiteBean>();
    private final int PRODUCETHREADCOUNT = 2;
    private boolean finished[] = new boolean[PRODUCETHREADCOUNT];
    private long timeGap = 15*60*1000;   //45 minutes.

    //Service Instances
    private FetchCityDistrictWeatherInfoService fetchCityDistrictWeatherInfoService = FetchCityDistrictWeatherInfoService.getFetchCityDistrictWeatherInfoServiceInstance();

    private static FetchCityDistrictWeatherApp fetchCityDistrictWeatherApp;
}

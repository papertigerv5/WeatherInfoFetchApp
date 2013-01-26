package app;

import models.dbmodels.WeatherInfoBean;
import models.locationInfo.CityDistrictBean;
import models.locationInfo.CountryBean;
import models.websitebeans.WebSiteString;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import service.DistrictWeatherInfoService;
import service.LoadCountryProvincesService;
import service.SessionFactoryService;
import service.URLInfoService;

import java.util.*;
import java.util.concurrent.*;

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

        app.startFetchWeatherInfoSerial();
    }


    public void reloadCountryBean(String countryName){
        countryBean = loadCountryProvincesService.InitCountryInfomation(countryName);
    }

    public void startFetchWeatherInfoSerial(){
        Session session = sessionFactory.openSession();
        for(CityDistrictBean cityDistrictBean : cityDistrictBeanList){
            WebSiteString webSiteString = downloadWebSiteString(cityDistrictBean);
            parseWebSiteStringToDB(webSiteString,session);
        }
    }

    public void startFetchWeatherInfo(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DownLoadWebResourceHelper[] produceThreads = new DownLoadWebResourceHelper[PRODUCETHREADCOUNT];
                ExecutorService executorService = Executors.newFixedThreadPool(5);
                for(int index = 0; index < PRODUCETHREADCOUNT; index++){
                    produceThreads[index] = new DownLoadWebResourceHelper(index);
                    executorService.submit(produceThreads[index]);
                }
                executorService.submit(new StoreWebInfoDBHelper());
                executorService.shutdown();
            }
        },0,timeGap);
    }

    private class DownLoadWebResourceHelper implements Runnable{
        private DownLoadWebResourceHelper(int n) {
            int size = cityDistrictBeanList.size();
            int unit = size/PRODUCETHREADCOUNT;
            startIndex = n*unit;
            if((n+1)*unit < size){
                endIndex = (n+1)*unit;
            } else {
                endIndex = size;
            }
        }

        @Override
        public void run() {
            int pIndex = startIndex;
            int count = 0;
            while(pIndex < endIndex){
                long currentMillls = System.currentTimeMillis();
                WebSiteString webSiteString = downloadWebSiteString(cityDistrictBeanList.get(pIndex++));
                long cost = System.currentTimeMillis() - currentMillls;
                System.out.println(" Download  "+(pIndex-1) + "   :   cost  " + cost + " mill seconds");
                count++;
                try {
                    productString.put(webSiteString);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                productString.put(new WebSiteString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private int startIndex;
        private int endIndex;
    }

    private class StoreWebInfoDBHelper implements Runnable{
        @Override
        public void run(){
            try {
                Session session = sessionFactory.openSession();
                WebSiteString siteString = productString.take();
                int count = 0;
                while(!siteString.isEndFlag()){
                    long currentMills = System.currentTimeMillis();
                    parseWebSiteStringToDB(siteString,session);
                    long cost = System.currentTimeMillis() - currentMills;
                    System.out.println(" Parse file  "+count + "   :   cost  " + cost + " mill seconds");
                    count++;
                    siteString = productString.take();
                }

            } catch (InterruptedException e) {

            }

        }
    }

    private WebSiteString downloadWebSiteString(CityDistrictBean cityDistrictBean){
        String sHtmlContent = urlInfoService.fetchHtmlStringById(cityDistrictBean.getDistrictNo());
        String htmlXmlContent = urlInfoService.fetchHtmlJsonStringById(cityDistrictBean.getDistrictNo());
        String xmlContent = urlInfoService.getXmlSringByDistrictId(cityDistrictBean.getDistrictNo());
        WebSiteString webSiteString = new WebSiteString(sHtmlContent,htmlXmlContent,xmlContent,cityDistrictBean);

        return webSiteString;
    }

    private WeatherInfoBean parseWebSiteStringToDB(WebSiteString siteString,Session session){
        int count = 0;
        session.beginTransaction();
        WeatherInfoBean weatherInfoBean = districtWeatherInfoService.getDistrictWeatherInfo(siteString);
        session.save(weatherInfoBean);
        session.getTransaction().commit();
        return weatherInfoBean;
    }

    private BlockingQueue<WebSiteString> productString = new LinkedBlockingQueue<WebSiteString>();
    private final int CACHECOUNT = 100;
    private final int PRODUCETHREADCOUNT = 4;
    private long timeGap = 60*60*1000;   //An hour.

    private Map<String,WeatherInfoBean> latestWeatherInfoMap = new HashMap<String, WeatherInfoBean>();
    private Map<String,Boolean> inValidDistrict = new HashMap<String, Boolean>();
    private DistrictWeatherInfoService districtWeatherInfoService = DistrictWeatherInfoService.getDistrictWeatherInfoServiceInstance();
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();
    private SessionFactory sessionFactory = SessionFactoryService.getSessionFactory();

    private static final String DEFAULTCOUNTRY = "china";
    private static LoadCountryProvincesService loadCountryProvincesService = LoadCountryProvincesService.getLoadCountryProvinceServiceInstance();

    private static CountryBean countryBean;

    static {
        countryBean = loadCountryProvincesService.InitCountryInfomation(DEFAULTCOUNTRY);
    }
    private static List<CityDistrictBean> cityDistrictBeanList = countryBean.getCityDistrictList();
}

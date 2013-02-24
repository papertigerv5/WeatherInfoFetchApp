package service;

import models.dbmodels.WeatherInfoBean;
import models.locationInfo.CityDistrictBean;
import models.locationInfo.CountryBean;
import models.websitebeans.WebSiteBean;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-2-17
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
public class FetchCityDistrictWeatherInfoService {

    public void startFetchCityDistrictScheduledActions(){
        fetchScheduledTimer.schedule(fetchCityWeatherInfoTimerTask,0,TIMEGAP);
    }

    public void startFetchCityDistrictAction(){
        int testCounts = 4;
        for (int cityDistrictIndex = 0; cityDistrictIndex < testCounts; cityDistrictIndex++){
            System.out.println("Start from " + cityDistrictIndex);
            CityDistrictBean cityDistrictBean = cityDistrictBeans.get(cityDistrictIndex);
            WebSiteBean webSiteBean = downloadWebSiteBeansFromCountryService.downLoadWebSiteBeanFromCityDistrictBean(cityDistrictBean);
            WeatherInfoBean weatherInfoBean = parseWebSiteBeanToWeatherInfoBeanService.parseWebSiteToWeatherInfoBean(webSiteBean);
            storeWeatherInfoBeanService.storeWeatherInfoBean(weatherInfoBean);
            System.out.println("End from " + cityDistrictIndex);
        }
    }

    public void reloadCountryInfo(String countryName){
        loadCountryProvincesService.reloadCityDistrictInfo(countryName);
    }

    public static FetchCityDistrictWeatherInfoService getFetchCityDistrictWeatherInfoServiceInstance(){
        if(fetchCityDistrictWeatherInfoService == null){
            fetchCityDistrictWeatherInfoService = new FetchCityDistrictWeatherInfoService();
        }

        return fetchCityDistrictWeatherInfoService;
    }

    private FetchCityDistrictWeatherInfoService(){
        cityDistrictBeans = CityDistrictBeanService.getCityDistrictBeanServiceInstance().findAllCityDistrictBeans();
        if(cityDistrictBeans == null || cityDistrictBeans.isEmpty()){
            loadCountryProvincesService.reloadCityDistrictInfo(LoadCountryProvincesService.DEFAULTCOUNTRY);
            cityDistrictBeans = CityDistrictBeanService.getCityDistrictBeanServiceInstance().findAllCityDistrictBeans();
        }

        for (int threadIndex = 0; threadIndex < DOWNLOADTHEADCOUNTS; threadIndex++){
            downloadJobs[threadIndex] = new DownLoadWebResourceHelper(threadIndex);
        }

        for (int threadIndex = 0; threadIndex < PARSEANDSAVETHREADCOUNTS; threadIndex++){
            parseAndSaveJobs[threadIndex] = new StoreWebInfoDBHelper();
        }

    }

    private class FetchCityWeatherInfoTimerTask extends TimerTask{
        @Override
        public void run(){
            for (int threadIndex = 0; threadIndex < DOWNLOADTHEADCOUNTS; threadIndex++){
                finished[threadIndex] = false;
                executorService.submit(downloadJobs[threadIndex]);
            }

            for (int threadIndex = 0; threadIndex < PARSEANDSAVETHREADCOUNTS; threadIndex++){
                executorService.submit(parseAndSaveJobs[threadIndex]);
            }
        }

    }

    private class DownLoadWebResourceHelper implements Runnable{
        private DownLoadWebResourceHelper(int number) {
            this.number = number;
            int size = cityDistrictBeans.size();
            int unit = size/DOWNLOADTHEADCOUNTS;
            startIndex = number*unit;
            if(number == DOWNLOADTHEADCOUNTS-1){
                endIndex = size;
            }   else {
                endIndex = (number+1)*unit;
            }

            System.out.println("Thread " + number + " start from " + startIndex + "  to  " + endIndex);
        }

        @Override
        public void run() {
            long currentMills = System.currentTimeMillis();
            int pIndex = startIndex;
            while(pIndex < endIndex){
                long currentMillls = System.currentTimeMillis();
                WebSiteBean webSiteBean = downloadWebSiteBeansFromCountryService.downLoadWebSiteBeanFromCityDistrictBean(cityDistrictBeans.get(pIndex++));
                long cost = System.currentTimeMillis() - currentMillls;
                System.out.println(" Download  "+(pIndex-1) + "   :   cost  " + cost + " mill seconds");
                try {
                    webSiteBeanStoreHouse.put(webSiteBean);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long cost = System.currentTimeMillis() - currentMills;
            finished[number] = true;
            System.out.println("Thead" + startIndex + " Cost : " + cost/1000/60);
            if(isLastFinished()){
                try {
                    webSiteBeanStoreHouse.put(new WebSiteBean());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean isLastFinished(){
            for(int index = 0; index < DOWNLOADTHEADCOUNTS; index++){
                if(!finished[index]){
                    return false;
                }
            }

            return true;
        }
        private int number;
        private int startIndex;
        private int endIndex;
    }

    private class StoreWebInfoDBHelper implements Runnable{
        @Override
        public void run(){
            int count = 0;
            try {
                System.out.println("before Get: ");
                WebSiteBean siteBean = webSiteBeanStoreHouse.take();
                System.out.println("after Get: ");
                while(!siteBean.isEndFlag()){
                    long currentMills = System.currentTimeMillis();
                    WeatherInfoBean weatherInfoBean = parseWebSiteBeanToWeatherInfoBeanService.parseWebSiteToWeatherInfoBean(siteBean);
                    System.out.println("IN Circle");
                    storeWeatherInfoBeanService.storeWeatherInfoBean(weatherInfoBean);
                    long cost = System.currentTimeMillis() - currentMills;
                    System.out.println(" Parse file  "+count + "   :   cost  " + cost + " mill seconds");
                    count++;
                    siteBean = webSiteBeanStoreHouse.take();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Try to recommand the jvm to restore its memory resource.!
            System.gc();
            System.out.println(count + " WeatherInfoBeans Saved the Finished");
        }
    }

    //Location Info beans.
    private CountryBean countryBean;
    private List<CityDistrictBean> cityDistrictBeans;

    //StoreHouse for WebSite Bean.
    private BlockingQueue<WebSiteBean> webSiteBeanStoreHouse = new LinkedBlockingQueue<WebSiteBean>();


    //Some parameters.
    private final int DOWNLOADTHEADCOUNTS = 10;
    private final int PARSEANDSAVETHREADCOUNTS = 1;
    private final long TIMEGAP = 30*60*1000;


    //Thread Resources.
    private ExecutorService executorService = Executors.newFixedThreadPool(DOWNLOADTHEADCOUNTS+PARSEANDSAVETHREADCOUNTS);
    private Runnable[] downloadJobs = new Runnable[DOWNLOADTHEADCOUNTS];
    private Runnable[] parseAndSaveJobs = new Runnable[PARSEANDSAVETHREADCOUNTS];

    //Thread end synchronized parameters.
    private boolean finished[] = new boolean[DOWNLOADTHEADCOUNTS];
    //Schedule Timers.
    private Timer fetchScheduledTimer = new Timer();

    //Services.
    private LoadCountryProvincesService loadCountryProvincesService = LoadCountryProvincesService.getLoadCountryProvinceServiceInstance();
    private DownloadWebSiteBeansFromCountryService downloadWebSiteBeansFromCountryService = DownloadWebSiteBeansFromCountryService.getDownloadWebSiteBeansFromCountryServiceInstance();
    private ParseWebSiteBeanToWeatherInfoBeanService parseWebSiteBeanToWeatherInfoBeanService = ParseWebSiteBeanToWeatherInfoBeanService.getParseWebSiteBeanToWeatherInfoBeanServiceInstance();
    private StoreWeatherInfoBeanService storeWeatherInfoBeanService = StoreWeatherInfoBeanService.getStoreWeatherInfoBeanServiceInstance();

    //Task Instance
    private FetchCityWeatherInfoTimerTask fetchCityWeatherInfoTimerTask = new FetchCityWeatherInfoTimerTask();

    //Myself service.
    private static FetchCityDistrictWeatherInfoService fetchCityDistrictWeatherInfoService;
}

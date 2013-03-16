package service;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-11
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
public class WeatherStatusService {

    /**
     * For unit test.
     * @param args
     */
    public static void main(String[] args){
        final WeatherStatusService service = WeatherStatusService.getWeatherStatueServiceInstance();
        String status = service.getCityDistrictWeatherStatusByDistrictId("101010800");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                service.getCityWeatherStatusById("101240101");
            }
        },0,5*60*1000);
    }

    public static WeatherStatusService getWeatherStatueServiceInstance(){
        if(weatherStatueService == null){
            weatherStatueService = new WeatherStatusService();
        }
        return weatherStatueService;
    }

    public String getCityWeatherStatusById(String cityNo){
        String url = URLHEADER + cityNo + ".shtml";
        List<Element> tableElements = htmlParseService.getTableElementsInDiv(cityNo,"weather6h");
        String status = null;
        for(Element tableElement : tableElements){
            status = htmlParseService.getContentByTableElement(tableElement,2,0);
            break;
        }

        return status;
    }

    public String getCityDistrictWeatherStatusByDistrictId(String districtId){
        String url = URLHEADER + districtId + ".shtml";
        List<Element> tableElements = htmlParseService.getTableElementsInDiv(url,"7d");

        String status = null;

        if(tableElements.size() > 3){
            status = htmlParseService.getContentByTableElement(tableElements.get(1),0,3);
        }

        return status;
    }





    private WeatherStatusService(){

    }

    private static final String URLHEADER = "http://www.weather.com.cn/weather/";

    private static WeatherStatusService weatherStatueService;

    private HtmlParseService htmlParseService = HtmlParseService.getHtmlParseServiceInstance();
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();
}

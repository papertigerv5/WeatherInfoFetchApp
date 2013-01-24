package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Calendar;
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
        List<Element> tableElements = getTableElementsInDiv(cityNo,"weather6h");
        String status = null;
        for(Element tableElement : tableElements){
            status = getContentByTableElement(tableElement,2,0);
            break;
        }

        return status;
    }

    public String getCityDistrictWeatherStatusByDistrictId(String districtId){
        List<Element> tableElements = getTableElementsInDiv(districtId,"7d");

        String status = null;

        if(tableElements.size() > 3){
            status = getContentByTableElement(tableElements.get(1),0,3);
        }

        return status;
    }



    private List<Element> getTableElementsInDiv(String id,String divId){
        Document document = getDocumentById(id);
        Element element = document.getElementById(divId);
        if(element == null){
            System.out.print(id+"   ");
        }
        return element.getElementsByTag("table");
    }

    private String getContentByTableElement(Element tableElement,int rowIndex,int columnIndex){
        List<Element> rowElements = tableElement.getElementsByTag("tr");
        if(rowIndex < rowElements.size()){
            Element rowElement = rowElements.get(rowIndex);
            List<Element> columnElements = rowElement.getElementsByTag("td");
            if(columnIndex < columnElements.size()){
                Element columnElement = columnElements.get(columnIndex);
                return columnElement.text();
            }
        }

        return null;
    }
    private Document getDocumentById(String id){
        String htmlUrl = URLHEADER + id + ".shtml";
        String htmlString  = urlInfoService.fetchInfoFromUrl(htmlUrl);
        Document document = Jsoup.parse(htmlString);

        return document;
    }


    private WeatherStatusService(){

    }

    private static final String URLHEADER = "http://www.weather.com.cn/weather/";

    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();
    private static WeatherStatusService weatherStatueService;
}

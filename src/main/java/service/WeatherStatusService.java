package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

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

    public String getCityWeatherStatusById(String htmlContent){
        List<Element> tableElements = getTableElementsInDiv(htmlContent,"weather6h");
        String status = null;
        for(Element tableElement : tableElements){
            status = getContentByTableElement(tableElement,2,0);
            break;
        }
        return status;
    }

    public String getCityDistrictWeatherStatusByDistrictId(String htmlContent){
        List<Element> tableElements = getTableElementsInDiv(htmlContent,"7d");
        String status = null;
        if(tableElements.size() > 3){
            status = getContentByTableElement(tableElements.get(1),0,3);
        }

        return status;
    }



    private List<Element> getTableElementsInDiv(String htmlContent,String divId){
        Document document = getDocumentById(htmlContent);
        Element element = document.getElementById(divId);
        if(element == null){
            return (List<Element>) Collections.EMPTY_LIST;
        } else {
            return element.getElementsByTag("table");
        }
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
    private Document getDocumentById(String htmlString){
        return Jsoup.parse(htmlString);
    }


    private WeatherStatusService(){

    }

    private static WeatherStatusService weatherStatueService;
}

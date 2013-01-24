package service;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.*;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import models.innerstorage.InTimeWeatherInfo;
import models.innerstorage.StaticWeatherInfoBean;
import models.locationInfo.CityDistrictBean;
import models.websitebeans.InTimeWeatherInfoJsonBean;
import models.websitebeans.InTimeWeatherInfoSiteBean;
import models.dbmodels.WeatherInfoBean;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-11
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public class DistrictWeatherInfoService {

    public static void main(String[] args){

    }

    public static DistrictWeatherInfoService getDistrictWeatherInfoServiceInstance(){
        if(service == null){
            service = new DistrictWeatherInfoService();
        }
        return service;
    }

    public WeatherInfoBean getDistrictWeatherInfo(CityDistrictBean cityDistrictBean){
        if(!stillValid()){
            staticWeatherInfoBean = getDistrictStaticWeatherInfoBean(cityDistrictBean);
        }
        InTimeWeatherInfoSiteBean inTimeWeatherInfoBean = getDistrictInTimeWeatherInfoById(cityDistrictBean.getDistrictNo());
        InTimeWeatherInfo inTimeWeatherInfo = new InTimeWeatherInfo(inTimeWeatherInfoBean);

        return new WeatherInfoBean(inTimeWeatherInfo,staticWeatherInfoBean,cityDistrictBean);
    }

    private InTimeWeatherInfoSiteBean getDistrictInTimeWeatherInfoById(String districtId){
        String districtUrl = WEATHERHEADER + districtId + ".html";

        String jsonString = urlInfoService.fetchInfoFromUrl(districtUrl);
        Gson gson = new Gson();
        InTimeWeatherInfoJsonBean districtDirectWeatherInfoBean = new InTimeWeatherInfoJsonBean();
        districtDirectWeatherInfoBean = gson.fromJson(jsonString,districtDirectWeatherInfoBean.getClass());

        return  districtDirectWeatherInfoBean.getWeatherinfo();
    }

    private StaticWeatherInfoBean getDistrictStaticWeatherInfoBean(CityDistrictBean cityDistrictBean){

        String sweetDegree = getAttributeByDIdAndAttributeName(cityDistrictBean.getDistrictNo(),StaticWeatherInfoBean.SWEETDEGREE);
        String waterCount  = getAttributeByDIdAndAttributeName(cityDistrictBean.getDistrictNo(),StaticWeatherInfoBean.WATERCOUNT);
        String temperature = getAttributeByDIdAndAttributeName(cityDistrictBean.getDistrictNo(),StaticWeatherInfoBean.TEMPERATURE);

        String status = "";
        if(cityDistrictBean.isCenterCity()){
            status = weatherStatusService.getCityWeatherStatusById(cityDistrictBean.getDistrictNo());
        }else{
            status = weatherStatusService.getCityDistrictWeatherStatusByDistrictId(cityDistrictBean.getDistrictNo());
        }

        return new StaticWeatherInfoBean(status,waterCount);
    }

    /**
     * TO-DO: Must judge if the static record is valid.
     * @return
     */
    private boolean stillValid(){

        return false;

    }

    private String getAttributeByDIdAndAttributeName(String districtId,String attributeName){
        String districtUrl = FLASHHEADER + districtId + ".xml";
        String sweetDegree = null;
        String xmlString = urlInfoService.fetchInfoFromUrl(districtUrl);
        try {
            Document document = DocumentHelper.parseText(xmlString);
            Element  element = document.getRootElement();
            List<Element> children = element.elements();
            Date currentDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            String hour = simpleDateFormat.format(currentDate);

            for(Element cElement : children){
                Attribute hourAttr = cElement.attribute("h");
                if(hour.equals(hourAttr.getValue())){
                    Attribute swAttr = cElement.attribute(attributeName);
                    sweetDegree = swAttr.getValue();
                    break;
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }


        return sweetDegree;
    }

    private DistrictWeatherInfoService(){

    }

    private StaticWeatherInfoBean staticWeatherInfoBean;
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();
    private WeatherStatusService weatherStatusService = WeatherStatusService.getWeatherStatueServiceInstance();

    private static final String WEATHERHEADER = "http://www.weather.com.cn/data/sk/";
    private static final String FLASHHEADER = "http://flash.weather.com.cn/sk2/";
    private static DistrictWeatherInfoService service;

}

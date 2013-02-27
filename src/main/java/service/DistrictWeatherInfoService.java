package service;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.*;


import models.websitebeans.WebSiteBean;
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

    public WeatherInfoBean getDistrictWeatherInfo(WebSiteBean webSiteString){
        if(!stillValid()){
            staticWeatherInfoBean = getDistrictStaticWeatherInfoBean(webSiteString);
        }
        InTimeWeatherInfoSiteBean inTimeWeatherInfoSiteBean = getDistrictInTimeWeatherInfoById(webSiteString);
        InTimeWeatherInfo inTimeWeatherInfo = new InTimeWeatherInfo(inTimeWeatherInfoSiteBean);

        return new WeatherInfoBean(inTimeWeatherInfo,staticWeatherInfoBean,webSiteString.getCityDistrictBean());

    }

    private InTimeWeatherInfoSiteBean getDistrictInTimeWeatherInfoById(WebSiteBean webSiteString){

        String xmlContent = webSiteString.getHttpXmlString();
        if(!webSiteString.isXmlFormatError()){
            Gson gson = new Gson();
            InTimeWeatherInfoJsonBean districtDirectWeatherInfoBean = new InTimeWeatherInfoJsonBean();
            districtDirectWeatherInfoBean = gson.fromJson(xmlContent,districtDirectWeatherInfoBean.getClass());

            return  districtDirectWeatherInfoBean.getWeatherinfo();
        }else{
            System.out.println(webSiteString.getCityDistrictBean().getDistrictNo() + "   :  " +xmlContent);
            return new InTimeWeatherInfoSiteBean();
        }
    }

    private StaticWeatherInfoBean getDistrictStaticWeatherInfoBean(WebSiteBean siteString){
        String waterCount = NOTSETERROR;
        CityDistrictBean cityDistrictBean = siteString.getCityDistrictBean();
        String xmlContent = siteString.getXmlContentString();
        if(!siteString.isXmlError()){
            List<Element> elements = parseStringToDocument(xmlContent);
            if(elements != null){
                waterCount  = getAttributeValueByElementsAndAttributeName(elements, StaticWeatherInfoBean.WATERCOUNT);
            }
        }

        String status = NOTSETERROR;
        String sHtmlContent = siteString.getHttpContentString();
        if(cityDistrictBean.isCenterCity()){
            status = weatherStatusService.getCityWeatherStatusById(sHtmlContent);
            if(status==null){
                status = weatherStatusService.getCityDistrictWeatherStatusByDistrictId(sHtmlContent);
            }
        }else{
            status = weatherStatusService.getCityDistrictWeatherStatusByDistrictId(sHtmlContent);
            if(status == null){
                status = weatherStatusService.getCityWeatherStatusById(sHtmlContent);
            }
        }

        if(status == null){
            status = NOTSETERROR;
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

    private List<Element> parseStringToDocument(String xmlContent){
        try{
            Document document = DocumentHelper.parseText(xmlContent);
            return document.getRootElement().elements();
        } catch (Exception ex){
            System.out.println(xmlContent);
            ex.printStackTrace();
        }
        return null;
    }

    private String getAttributeValueByElementsAndAttributeName(List<Element> children, String attributeName){

        String attributeValue = "";
        if(children != null && !children.isEmpty()){

            Date currentDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            String hour = simpleDateFormat.format(currentDate);

            for(Element cElement : children){
                Attribute hourAttr = cElement.attribute("h");
                if(hour.equals(hourAttr.getValue())){
                    Attribute swAttr = cElement.attribute(attributeName);
                    attributeValue = swAttr.getValue();
                    break;
                }
            }
        }
        return attributeValue;
    }

    private DistrictWeatherInfoService(){

    }

    private StaticWeatherInfoBean staticWeatherInfoBean;
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();
    private WeatherStatusService weatherStatusService = WeatherStatusService.getWeatherStatueServiceInstance();


    private static final String NOTSETERROR = "没有设置属性";

    private static DistrictWeatherInfoService service;

}

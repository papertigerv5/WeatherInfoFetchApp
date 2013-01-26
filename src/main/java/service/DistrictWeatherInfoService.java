package service;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


import models.websitebeans.WebSiteString;
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

    public WeatherInfoBean getDistrictWeatherInfo(WebSiteString webSiteString){
        if(!stillValid()){
            staticWeatherInfoBean = getDistrictStaticWeatherInfoBean(webSiteString);
        }
        InTimeWeatherInfoSiteBean inTimeWeatherInfoSiteBean = getDistrictInTimeWeatherInfoById(webSiteString);
        InTimeWeatherInfo inTimeWeatherInfo = new InTimeWeatherInfo(inTimeWeatherInfoSiteBean);

        return new WeatherInfoBean(inTimeWeatherInfo,staticWeatherInfoBean,webSiteString.getCityDistrictBean());

    }

    private InTimeWeatherInfoSiteBean getDistrictInTimeWeatherInfoById(WebSiteString webSiteString){

        String xmlContent = webSiteString.getHttpXmlString();
        if(!webSiteString.isXmlFormatError()){
            Gson gson = new Gson();
            InTimeWeatherInfoJsonBean districtDirectWeatherInfoBean = new InTimeWeatherInfoJsonBean();
            districtDirectWeatherInfoBean = gson.fromJson(xmlContent,districtDirectWeatherInfoBean.getClass());

            return  districtDirectWeatherInfoBean.getWeatherinfo();
        }else{
            System.out.println(xmlContent);
            return null;
        }
    }

    private StaticWeatherInfoBean getDistrictStaticWeatherInfoBean(WebSiteString siteString){
        String waterCount = NOTSETERROR;
        CityDistrictBean cityDistrictBean = siteString.getCityDistrictBean();
        String xmlContent = siteString.getXmlContentString();
        if(!siteString.isXmlError()){
            List<Element> elements = parseStringToDocument(xmlContent);
            if(elements == null){
                waterCount = NOTSETERROR;
                writeNotSatisfiedCityDistrict(cityDistrictBean);
                cityDistrictBean.setSetWaterCount(Boolean.FALSE);
            } else {
                waterCount  = getAttributeValueByElementsAndAttributeName(elements, StaticWeatherInfoBean.WATERCOUNT);
            }
        }

        String status = "";
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

        return new StaticWeatherInfoBean(status,waterCount);
    }

    private void writeNotSatisfiedCityDistrict(CityDistrictBean cityDistrictBean){
        File file = new File("noneDistrict.txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.write(cityDistrictBean.getDistrictNo() + " : " + cityDistrictBean.getDiscritName());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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

package service;

import models.dbmodels.WeatherInfoBean;
import models.websitebeans.WebSiteBean;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-2-17
 * Time: 下午9:30
 * To change this template use File | Settings | File Templates.
 */
public class ParseWebSiteBeanToWeatherInfoBeanService {


    public static ParseWebSiteBeanToWeatherInfoBeanService getParseWebSiteBeanToWeatherInfoBeanServiceInstance(){
        if(parseWebSiteBeanToWeatherInfoBeanService == null){
            parseWebSiteBeanToWeatherInfoBeanService = new ParseWebSiteBeanToWeatherInfoBeanService();
        }
        return parseWebSiteBeanToWeatherInfoBeanService;
    }
    public WeatherInfoBean parseWebSiteToWeatherInfoBean(WebSiteBean webSiteBean){
//        return districtWeatherInfoService.getDistrictWeatherInfo(webSiteBean);
        return null;
    }


    private ParseWebSiteBeanToWeatherInfoBeanService(){

    }

    private static ParseWebSiteBeanToWeatherInfoBeanService parseWebSiteBeanToWeatherInfoBeanService;

    private DistrictWeatherInfoService districtWeatherInfoService = DistrictWeatherInfoService.getDistrictWeatherInfoServiceInstance();
}

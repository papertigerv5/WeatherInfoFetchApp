package service;

import models.locationInfo.CityDistrictBean;
import models.websitebeans.WebSiteBean;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-2-17
 * Time: 下午9:12
 * To change this template use File | Settings | File Templates.
 */
public class DownloadWebSiteBeansFromCountryService {

    public static DownloadWebSiteBeansFromCountryService getDownloadWebSiteBeansFromCountryServiceInstance(){
        if(downloadWebSiteBeansFromCountryService == null){
            downloadWebSiteBeansFromCountryService = new DownloadWebSiteBeansFromCountryService();
        }
        return downloadWebSiteBeansFromCountryService;
    }

    /**
     *
     * @param cityDistrictBean
     * @return
     */
    public WebSiteBean downLoadWebSiteBeanFromCityDistrictBean(CityDistrictBean cityDistrictBean){
        String sHtmlContent = urlInfoService.fetchHtmlStringById(cityDistrictBean.getDistrictNo());
        String htmlXmlContent = urlInfoService.fetchHtmlJsonStringById(cityDistrictBean.getDistrictNo());
        String xmlContent = urlInfoService.getXmlSringByDistrictId(cityDistrictBean.getDistrictNo());

        return new WebSiteBean(sHtmlContent,htmlXmlContent,xmlContent,cityDistrictBean);
    }


    private WebSiteBean webSiteBean = new WebSiteBean();
    private static DownloadWebSiteBeansFromCountryService downloadWebSiteBeansFromCountryService;
    private URLInfoService urlInfoService = URLInfoService.getURLInfoServiceInstance();
}

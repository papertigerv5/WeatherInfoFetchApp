package service;

import models.dbmodels.RiverWaterStatisticsBean;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class RiverWaterInfoService {

    public static void main(String[] args){
        RiverWaterInfoService riverWaterInfoService1 = RiverWaterInfoService.getDefaultRiverWaterInfoService();
        riverWaterInfoService1.fetchCurrentAllRiverWaterStatistics();
    }

    public static RiverWaterInfoService getDefaultRiverWaterInfoService(){
        if(riverWaterInfoService == null){
            riverWaterInfoService = new RiverWaterInfoService();
        }

        return riverWaterInfoService;
    }

    public static RiverWaterInfoService getRiverWaterInfoService(String riverCourserUrlHeader){
        if(riverCourserUrlHeader == null || riverWaterInfoService == null){
            riverWaterInfoService = new RiverWaterInfoService(riverCourserUrlHeader);
        }

        return riverWaterInfoService;
    }
    public List<RiverWaterStatisticsBean> fetchCurrentAllRiverWaterStatistics(){

        String remoteTotalString = httpClientFetchService.getDWRString(DEFAULTRIVERCOURSEURLHEADER);

        //TO-DO: fetch the real data.
        return null;
    }

    public static final String DEFAULTRIVERCOURSEURLHEADER = "http://xxfb.hydroinfo.gov.cn/dwr/call/plaincall/IndexDwr.getZDHD_SSSQ.dwr";

    private RiverWaterStatisticsBean parseSingleRiverWaterStatistics(String riverWaterStatisticsInfo){
        //TO-DO: To fetch the real data.
        return null;
    }

    private RiverWaterInfoService(){
        this.riverCourseUrlHeader = DEFAULTRIVERCOURSEURLHEADER;
    }

    private RiverWaterInfoService(String riverCourseUrlHeader){
        this.riverCourseUrlHeader = riverCourseUrlHeader;
    }


    private static RiverWaterInfoService riverWaterInfoService;

    //Inner Elements;
    private String riverCourseUrlHeader;
    private String reservoirUrlHeader;

    //Service Instances;
    private HttpClientFetchService httpClientFetchService = HttpClientFetchService.getHttpClientFetchServiceInstance();
}

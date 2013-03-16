package service;

import models.dbmodels.RiverWaterStatisticsBean;
import models.dbmodels.WaterStationStatisticsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-16
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
public class ReservoirInfoService {

    public static void main(String[] args){
        ReservoirInfoService reservoirInfoService1 = ReservoirInfoService.getReservoirInfoServiceInstance();
        reservoirInfoService1.fetchCurrentAllReservoirInfo();
    }

    /**
     * Get ReservoirInfoService instance.
     * @return
     */
    public static ReservoirInfoService getReservoirInfoServiceInstance(){
        if (reservoirInfoService == null){
            reservoirInfoService = new ReservoirInfoService();
        }

        return reservoirInfoService;
    }

    /**
     * Get ReservoirInfo Service Instance...
     * @param wholeUrl
     * @return
     */
    public static ReservoirInfoService getReservoirInfoServiceInstance(String wholeUrl){
        if(wholeUrl == null || reservoirInfoService == null){
            reservoirInfoService = new ReservoirInfoService(wholeUrl);
        }

        return reservoirInfoService;
    }


    public List<WaterStationStatisticsBean> fetchCurrentAllReservoirInfo(){
        String remoteWholeString = httpClientFetchService.getDWRString(DEFAULTURL);
        remoteWholeString = unicodeConvertService.convertUnicodeStringToChinese(remoteWholeString);

        List<ArrayList<String>> tableTextList = htmlParseService.parseDWRString(remoteWholeString);

        List<WaterStationStatisticsBean> waterStationBeans = new ArrayList<WaterStationStatisticsBean>();

        for(ArrayList<String> rowTextList : tableTextList){
            WaterStationStatisticsBean waterStationBean = new WaterStationStatisticsBean();
            if(rowTextList.size() != RiverWaterStatisticsBean.PROPERTYCOUNT){
                System.out.println("ERROR");
                break;
            }
            waterStationBean.setValues(rowTextList);
            waterStationBeans.add(waterStationBean);
        }
        //TO-DO: fetch the real data.
        return waterStationBeans;
    }
    public String getReservoirInfoDWRString(){
        return httpClientFetchService.getDWRString(wholeUrl);
    }


    private ReservoirInfoService(){
        this.wholeUrl = DEFAULTURL;
    }

    private ReservoirInfoService(String wholeUrl){
        this.wholeUrl = wholeUrl;
    }

    //private properties.
    private String wholeUrl;

    //private services.
    private HttpClientFetchService httpClientFetchService = HttpClientFetchService.getHttpClientFetchServiceInstance();
    private UnicodeConvertService unicodeConvertService = UnicodeConvertService.getUnicodeConvertServiceInstance();
    private HtmlParseService htmlParseService = HtmlParseService.getHtmlParseServiceInstance();

    //private static properties.
    private static final String DEFAULTURL = "http://xxfb.hydroinfo.gov.cn/dwr/call/plaincall/IndexDwr.getZDSK_SSSQ.dwr";



    //private static instances.
    private static ReservoirInfoService reservoirInfoService;

}

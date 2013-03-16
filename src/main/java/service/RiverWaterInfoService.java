package service;

import models.dbmodels.RiverWaterStatisticsBean;
import org.json.JSONException;
import org.json.JSONTokener;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class RiverWaterInfoService {

    public static void main(String[] args) throws JSONException {
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
    public List<RiverWaterStatisticsBean> fetchCurrentAllRiverWaterStatistics() throws JSONException {

        String remoteTotalString = httpClientFetchService.getDWRString(DEFAULTRIVERCOURSEURLHEADER);

        remoteTotalString = unicodeConvertService.convertUnicodeStringToChinese(remoteTotalString);

        List<ArrayList<String>> tableTextList = htmlParseService.parseDWRString(remoteTotalString);

        List<RiverWaterStatisticsBean> riverWaterStatisticsBeans = new ArrayList<RiverWaterStatisticsBean>();

        for(ArrayList<String> rowTextList : tableTextList){
            RiverWaterStatisticsBean riverWaterStatisticsBean = new RiverWaterStatisticsBean();
            if(rowTextList.size() != RiverWaterStatisticsBean.PROPERTYCOUNT){
                System.out.println("ERROR");
                break;
            }
            riverWaterStatisticsBean.setValues(rowTextList);
            riverWaterStatisticsBeans.add(riverWaterStatisticsBean);
        }
        //TO-DO: fetch the real data.
        return riverWaterStatisticsBeans;
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
    private HtmlParseService htmlParseService = HtmlParseService.getHtmlParseServiceInstance();
    private UnicodeConvertService unicodeConvertService = UnicodeConvertService.getUnicodeConvertServiceInstance();
}

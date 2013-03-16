package service;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-16
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */
public class ReservoirInfoService {


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

    //private static properties.
    private static final String DEFAULTURL = "http://xxfb.hydroinfo.gov.cn/dwr/call/plaincall/IndexDwr.getZDSK_SSSQ.dwr";

    //private static instances.
    private static ReservoirInfoService reservoirInfoService;

}

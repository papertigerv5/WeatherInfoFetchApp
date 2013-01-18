package models.websitebeans;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-11
 * Time: 上午11:51
 * To change this template use File | Settings | File Templates.
 */
public class InTimeWeatherInfoSiteBean {

    public String getCity() {
        return city;
    }

    public String getCityid() {
        return cityid;
    }

    public String getTemp() {
        return temp;
    }

    public String getWD() {
        return WD;
    }

    public String getWS() {
        return WS;
    }

    public String getSD() {
        return SD;
    }

    public String getWSE() {
        return WSE;
    }

    public String getTime() {
        return time;
    }

    /**
     * Must override this method to check if the object identical.
     * @param obj (the object to be compared.)
     * @return
     *
     *    true: if the publish time haven't been changed.
     *    false: otherwise.
     */
    @Override
    public boolean equals(Object obj){
        InTimeWeatherInfoSiteBean directFetchedWeatherInfoBean = (InTimeWeatherInfoSiteBean)obj;


        return time.equals(directFetchedWeatherInfoBean.time);

//        return city.equals(directFetchedWeatherInfoBean.city) &&
//                cityid.equals(directFetchedWeatherInfoBean.cityid) &&
//                 temp.equals(directFetchedWeatherInfoBean.temp) &&
//                    WD.equals(directFetchedWeatherInfoBean.WD) &&
//                        WS.equals(directFetchedWeatherInfoBean.WS) &&
//                            SD.equals(directFetchedWeatherInfoBean.SD) &&
//                                WSE.equals(directFetchedWeatherInfoBean.WSE);
    }

    private String city;
    private String cityid;
    private String temp;
    private String WD;
    private String WS;
    private String SD;
    private String WSE;
    private String time;
}


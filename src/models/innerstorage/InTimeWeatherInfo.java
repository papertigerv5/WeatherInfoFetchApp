package models.innerstorage;

import models.utils.DateUtils;
import models.websitebeans.InTimeWeatherInfoSiteBean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-14
 * Time: 下午10:13             1
 * To change this template use File | Settings | File Templates.
 */
public class InTimeWeatherInfo {

    public InTimeWeatherInfo(InTimeWeatherInfoSiteBean directFetchedWeatherInfoBean){
        temperature = directFetchedWeatherInfoBean.getTemp();
        sweetDegree = directFetchedWeatherInfoBean.getSD();
        windDirection = directFetchedWeatherInfoBean.getWD();
        windLevel = directFetchedWeatherInfoBean.getWS();
        publishHourInfo = directFetchedWeatherInfoBean.getTime();

        publishDateInfo = DateUtils.getDateFormat(new Date());
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSweetDegree() {
        return sweetDegree;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindLevel() {
        return windLevel;
    }

    public String getPublishDateInfo() {
        return publishDateInfo;
    }

    public String getPublishHourInfo() {
        return publishHourInfo;
    }

    @Override
    public boolean equals(Object obj){
        InTimeWeatherInfo inTimeWeatherRecord = (InTimeWeatherInfo)obj;

        return this == obj ||
                 (this != null && inTimeWeatherRecord != null && publishHourInfo.equals(inTimeWeatherRecord.publishHourInfo));
    }

    private String temperature;
    private String sweetDegree;
    private String windDirection;
    private String windLevel;

    private String publishDateInfo;
    private String publishHourInfo;
}

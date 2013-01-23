package models.dbmodels;

import models.innerstorage.InTimeWeatherInfo;
import models.innerstorage.StaticWeatherInfoBean;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-23
 * Time: 下午9:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class WeatherInfoBean {

    public WeatherInfoBean() {
    }

    public WeatherInfoBean(InTimeWeatherInfo inTimeWeatherInfo, StaticWeatherInfoBean staticWeatherInfoBean) {

        temperature = inTimeWeatherInfo.getTemperature();
        sweetDegree = inTimeWeatherInfo.getSweetDegree();
        windDirection = inTimeWeatherInfo.getWindDirection();
        windLevel = inTimeWeatherInfo.getWindLevel();
        publishDateInfo = inTimeWeatherInfo.getPublishDateInfo();
        publishHourInfo = inTimeWeatherInfo.getPublishHourInfo();

        status = staticWeatherInfoBean.getStatus();
        waterCount = staticWeatherInfoBean.getWaterCount();

        //TO-DO: add airPressure and evaporation. Not yet because haven't found where the information stores.
    }

    private long id;

    @GeneratedValue
    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String temperature;

    @Basic
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    private String sweetDegree;

    @Basic
    public String getSweetDegree() {
        return sweetDegree;
    }

    public void setSweetDegree(String sweetDegree) {
        this.sweetDegree = sweetDegree;
    }

    private String windDirection;

    @Basic
    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    private String windLevel;

    @Basic
    public String getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }

    private String publishDateInfo;

    @Basic
    public String getPublishDateInfo() {
        return publishDateInfo;
    }

    public void setPublishDateInfo(String publishDateInfo) {
        this.publishDateInfo = publishDateInfo;
    }

    private String publishHourInfo;

    @Basic
    public String getPublishHourInfo() {
        return publishHourInfo;
    }

    public void setPublishHourInfo(String publishHourInfo) {
        this.publishHourInfo = publishHourInfo;
    }

    private String status;

    @Basic
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String waterCount;

    @Basic
    public String getWaterCount() {
        return waterCount;
    }

    public void setWaterCount(String waterCount) {
        this.waterCount = waterCount;
    }

    private String airPressure;

    @Basic
    public String getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(String airPressure) {
        this.airPressure = airPressure;
    }

    private String evaporation;

    @Basic
    public String getEvaporation() {
        return evaporation;
    }

    public void setEvaporation(String evaporation) {
        this.evaporation = evaporation;
    }

}

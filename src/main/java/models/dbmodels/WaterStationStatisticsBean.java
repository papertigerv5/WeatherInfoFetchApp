package models.dbmodels;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class WaterStationStatisticsBean {

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getWaterStationName() {
        return waterStationName;
    }

    public void setWaterStationName(String waterStationName) {
        this.waterStationName = waterStationName;
    }

    public String getStationWaterLevel() {
        return stationWaterLevel;
    }

    public void setStationWaterLevel(String stationWaterLevel) {
        this.stationWaterLevel = stationWaterLevel;
    }

    public String getInStationFlowCount() {
        return inStationFlowCount;
    }

    public void setInStationFlowCount(String inStationFlowCount) {
        this.inStationFlowCount = inStationFlowCount;
    }

    public String getDamTopHeight() {
        return damTopHeight;
    }

    public void setDamTopHeight(String damTopHeight) {
        this.damTopHeight = damTopHeight;
    }

    public String getPublishDateInfo() {
        return publishDateInfo;
    }

    public void setPublishDateInfo(String publishDateInfo) {
        this.publishDateInfo = publishDateInfo;
    }

    public String getPubllishHourInfo() {
        return publlishHourInfo;
    }

    public void setPubllishHourInfo(String publlishHourInfo) {
        this.publlishHourInfo = publlishHourInfo;
    }

    private String riverName;
    private String waterStationName;
    private String stationWaterLevel;
    private String inStationFlowCount;
    private String damTopHeight;
    private String publishDateInfo;
    private String publlishHourInfo;

    private Long publishDateValue;

}

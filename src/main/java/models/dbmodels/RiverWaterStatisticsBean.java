package models.dbmodels;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class RiverWaterStatisticsBean {

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddr() {
        return stationAddr;
    }

    public void setStationAddr(String stationAddr) {
        this.stationAddr = stationAddr;
    }

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }


    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }

    public String getFlowCount() {
        return flowCount;
    }

    public void setFlowCount(String flowCount) {
        this.flowCount = flowCount;
    }

    public String getWarningWaterLevel() {
        return warningWaterLevel;
    }

    public void setWarningWaterLevel(String warningWaterLevel) {
        this.warningWaterLevel = warningWaterLevel;
    }

    public String getPublishDateInfo() {
        return publishDateInfo;
    }

    public void setPublishDateInfo(String publishDateInfo) {
        this.publishDateInfo = publishDateInfo;
    }

    public String getPublishHourInfo() {
        return publishHourInfo;
    }

    public void setPublishHourInfo(String publishHourInfo) {
        this.publishHourInfo = publishHourInfo;
    }

    public void setValues(List<String> properties){
        stationName = properties.get(0);
        stationAddr = properties.get(1);
        riverName = properties.get(2);
        waterLevel = properties.get(3);
        flowCount = properties.get(4);
        String dateInfo[] = properties.get(5).split(" ");
        Calendar calendar = Calendar.getInstance();
        publishDateInfo = calendar.get(Calendar.YEAR) + "-" + dateInfo[0];
        publishHourInfo = dateInfo[1];
        warningWaterLevel = properties.get(6);
    }

    public static final int PROPERTYCOUNT = 7;

    private String riverName;

    private String stationName;

    private String stationAddr;

    private String waterLevel;

    private String flowCount;

    private String warningWaterLevel;

    private long publishDateValue;

    private String publishDateInfo;

    private String publishHourInfo;
}

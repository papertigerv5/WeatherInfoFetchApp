package models.dbmodels;

import javax.persistence.Entity;
import java.util.Calendar;
import java.util.List;

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

    public String getPublishHourInfo() {
        return publishHourInfo;
    }

    public void setPublishHourInfo(String publishHourInfo) {
        this.publishHourInfo = publishHourInfo;
    }

    public void setValues(List<String> values){
        waterStationName = values.get(0);
        waterStationAddr = values.get(1);
        riverName = values.get(2);
        stationWaterLevel = values.get(3);
        inStationFlowCount = values.get(4);
        String dateInfo[] = values.get(5).split(" ");
        Calendar calendar = Calendar.getInstance();
        publishDateInfo = calendar.get(Calendar.YEAR) + "-" + dateInfo[0];
        publishHourInfo = dateInfo[1];
        damTopHeight = values.get(6);
    }

    private String waterStationName;
    private String waterStationAddr;
    private String riverName;
    private String stationWaterLevel;
    private String inStationFlowCount;
    private String damTopHeight;
    private String publishDateInfo;
    private String publishHourInfo;

    private Long publishDateValue;

}

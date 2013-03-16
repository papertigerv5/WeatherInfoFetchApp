package models.dbmodels;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public class RiverWaterStatisticsBean {

    public String getRiverName() {
        return riverName;
    }

    public void setRiverName(String riverName) {
        this.riverName = riverName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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

    private String riverName;

    private String stationName;

    private String waterLevel;

    private String flowCount;

    private String warningWaterLevel;

    private long publishDateValue;

    private String publishDateInfo;

    private String publishHourInfo;
}

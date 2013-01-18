package models.innerstorage;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-17
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 */
public class StaticWeatherInfoBean {

    public static final String SWEETDEGREE   = "sd";
    public static final String WATERCOUNT    = "js";
    public static final String TEMPERATURE   = "wd";
    public static final String TABLE = "";

    public StaticWeatherInfoBean(String status,String waterCount) {
        this.status = status;
        this.waterCount = waterCount;
    }

    public String getStatus() {
        return status;
    }

    public String getWaterCount() {
        return waterCount;
    }

    private String status;
    private String waterCount;
    private String airPressure;
    private String evaporation;

}

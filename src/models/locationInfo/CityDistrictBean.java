package models.locationInfo;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:49
 * To change this template use File | Settings | File Templates.
 */
public class CityDistrictBean {

    public CityDistrictBean(String cityNo, String districtNo, String districtName,boolean city) {
        this.cityNo = cityNo;
        this.districtNo = districtNo;
        this.discritName = districtName;
        this.city = city;
    }

    public String getCityNo() {
        return cityNo;
    }

    public String getDistrictNo() {
        return districtNo;
    }

    public boolean isCity() {
        return city;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public void setDistrictNo(String districtNo) {
        this.districtNo = districtNo;
    }

    public void setDiscritName(String discritName) {
        this.discritName = discritName;
    }

    @Override
    public String toString(){
        return discritName;
    }
    private String cityNo;
    private String districtNo;
    private String discritName;
    private boolean city;
}

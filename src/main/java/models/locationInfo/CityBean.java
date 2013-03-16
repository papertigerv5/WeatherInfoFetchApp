package models.locationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:45
 * To change this template use File | Settings | File Templates.
 */
public class CityBean {

    public CityBean(String provinceNo, String cityNo, String cityName) {
        this.provinceNo = provinceNo;
        this.cityNo = provinceNo + cityNo;
        this.cityName = cityName;
        this.districtBeanList = new ArrayList<CityDistrictBean>();
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public String getCityName() {
        return cityName;
    }

    public List<CityDistrictBean> getDistrictBeanList() {
        return districtBeanList;
    }

    @Override
    public String toString(){
        return cityName;
    }

    private String provinceNo;
    private String cityNo;
    private String cityName;
    private List<CityDistrictBean> districtBeanList;

}

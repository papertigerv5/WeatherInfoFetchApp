package models.locationInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:48
 * To change this template use File | Settings | File Templates.
 */
public class ProvinceBean {

    public ProvinceBean(String provinceNo, String provinceName) {
        this.provinceNo = provinceNo;
        this.provinceName = provinceName;
        cities = new ArrayList<CityBean>();
    }



    public String getProvinceName() {
        return provinceName;
    }

    public List<CityBean> getCities() {
        return cities;
    }

    @Override
    public String toString(){
        return provinceName;
    }

    private String countryNo;
    private String provinceNo;
    private String provinceName;
    private List<CityBean> cities;
}

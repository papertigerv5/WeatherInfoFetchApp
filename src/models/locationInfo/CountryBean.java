package models.locationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:50
 * To change this template use File | Settings | File Templates.
 */
public class CountryBean {

    public CountryBean(String countryName) {
        this.countryName = countryName;
        provinceBeanList = new ArrayList<ProvinceBean>();
    }

    public String getCountryNo() {
        return countryNo;
    }

    public String getCountryName() {
        return countryName;
    }

    public List<ProvinceBean> getProvinceBeanList() {
        return provinceBeanList;
    }

    public List<CityDistrictBean> getCityDistrictList(){
        List<CityDistrictBean> cityDistrictBeanList = new ArrayList<CityDistrictBean>();
        for(ProvinceBean provinceBean : provinceBeanList){
            for(CityBean cityBean : provinceBean.getCities()){
                   cityDistrictBeanList.addAll(cityBean.getDistrictBeanList());
            }
        }

        return cityDistrictBeanList;
    }

    @Override
    public String toString(){
        StringBuilder sbr = new StringBuilder();

        for(ProvinceBean bean : provinceBeanList){
            sbr.append(bean.getProvinceName());
        }
        return sbr.toString();
    }

    private CountryBean() {
        this.countryNo = "518";
        this.countryName = "china";
    }

    private String countryNo;
    private String countryName;
    private List<ProvinceBean> provinceBeanList;
}

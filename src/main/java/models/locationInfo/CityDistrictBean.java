package models.locationInfo;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class CityDistrictBean {

    public CityDistrictBean(String provinceNo,String cityNo, String districtNo, String districtName,boolean centerCity) {
        this.provinceNo = provinceNo;
        this.cityNo = cityNo;
        this.districtNo = districtNo;
        if(isSpecialProvince() || isSpecialDistrictNo()){
            this.districtNo = districtNo;
        } else {
            this.districtNo = cityNo + districtNo;
        }
        this.discritName = districtName;
        this.centerCity = centerCity;
    }

    public String getCityNo() {
        return cityNo;
    }

    public String getDistrictNo() {
        return districtNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public String getDiscritName() {
        return discritName;
    }

    public boolean isCenterCity() {
        return centerCity;
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

    public boolean isSpecialProvince() {
        return provinceNo.equals(specialProvinceNo);
    }

    public boolean isSpecialDistrictNo() {
        return specialDistrictNoList.contains(districtNo);
    }

    @Override
    public String toString(){
        return districtNo;
    }
    private String provinceNo;
    @Basic
    private String cityNo;
    private String districtNo;
    private String discritName;
    private boolean centerCity;
    private boolean specialProvince;
    private boolean specialCity;

    private static String specialProvinceNo = "10131";
    private static List<String> specialDistrictNoList = Arrays.asList(new String[]{"101201406","101081108"});
}

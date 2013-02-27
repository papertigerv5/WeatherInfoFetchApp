package models.locationInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public CityDistrictBean(){}

    public CityDistrictBean(String provinceNo,String cityNo, String districtNo, String districtName) {
        this.provinceNo = provinceNo;
        this.discritName = districtName;
        this.cityNo = cityNo;
        this.districtNo = districtNo;

        if(!isDirectCity(provinceNo)){
            if(isSpecialProvince() || isSpecialDistrictNo()){
                this.districtNo = districtNo;
            } else {
                this.districtNo = cityNo + districtNo;
            }
            centerCity = this.districtNo.endsWith(CENTERCITYNO);
        } else{
            this.districtNo = provinceNo + districtNo + DIRECTCITYTAIL;
            centerCity = this.districtNo.substring(0,this.districtNo.length()-2).endsWith(CENTERCITYNO);

        }

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

    public void setCenterCity(boolean centerCity) {
        this.centerCity = centerCity;
    }

    /**
     * Check if the city is governed by the part directly.
     * @param provinceNo: the given city id.
     * @return: if the districtNo is from a direct city governed by party directly.
     */
    public static boolean isDirectCity(String provinceNo){
        return provinceNo.equals("10101") ||
                provinceNo.equals("10102") ||
                provinceNo.equals("10103") ||
                provinceNo.equals("10104");

    }

    @Override
    public String toString(){
        return districtNo;
    }

    public boolean isSpecialCity() {
        return specialCity;
    }

    public void setSpecialCity(boolean specialCity) {
        this.specialCity = specialCity;
    }

    public static String getDIRECTCITYTAIL() {
        return DIRECTCITYTAIL;
    }

    public static void setDIRECTCITYTAIL(String DIRECTCITYTAIL) {
        CityDistrictBean.DIRECTCITYTAIL = DIRECTCITYTAIL;
    }

    public static String getCENTERCITYNO() {
        return CENTERCITYNO;
    }

    public static void setCENTERCITYNO(String CENTERCITYNO) {
        CityDistrictBean.CENTERCITYNO = CENTERCITYNO;
    }

    public static String getSpecialProvinceNo() {
        return specialProvinceNo;
    }

    public static void setSpecialProvinceNo(String specialProvinceNo) {
        CityDistrictBean.specialProvinceNo = specialProvinceNo;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String provinceNo;
    private String cityNo;
    private String districtNo;
    private String discritName;
    private boolean centerCity;
    private boolean specialProvince;
    private boolean specialCity;

    public static String DIRECTCITYTAIL = "00";
    public static String CENTERCITYNO = "01";
    private static String specialProvinceNo = "10131";
    private static List<String> specialDistrictNoList = Arrays.asList(new String[]{"101201406","101081108"});
}

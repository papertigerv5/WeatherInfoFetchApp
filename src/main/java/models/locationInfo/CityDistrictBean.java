package models.locationInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午7:49
 * To change this template use File | Settings | File Templates.
 */
public class CityDistrictBean {

    public CityDistrictBean(String provinceNo,String cityNo, String districtNo, String districtName) {
        this.provinceNo = provinceNo;
        this.cityNo = cityNo;
        this.districtNo = districtNo;
        if(isSpecialProvince() || isSpecialDistrictNo()){
            this.districtNo = districtNo;
        } else {
            this.districtNo = cityNo + districtNo;
        }
        if(!isDirectCity(districtNo)){
            centerCity = districtNo.endsWith(CENTERCITYNO);
        } else{
            centerCity = districtNo.substring(0,districtNo.length()-2).endsWith(CENTERCITYNO);
        }
        this.discritName = districtName;

        //Default set the flag of the waterCount is true.
        this.setWaterCount = true;
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

    public boolean isSetWaterCount() {
        return setWaterCount;
    }

    public void setSetWaterCount(boolean setWaterCount) {
        this.setWaterCount = setWaterCount;
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
     * @param districtNo: the given city id.
     * @return: if the districtNo is from a direct city governed by party directly.
     */
    public static boolean isDirectCity(String districtNo){
        return districtNo.startsWith("10101") ||
                districtNo.startsWith("10102") ||
                districtNo.startsWith("10103") ||
                districtNo.startsWith("10104");

    }

    @Override
    public String toString(){
        return districtNo;
    }
    private String provinceNo;
    private String cityNo;
    private String districtNo;
    private String discritName;
    private boolean centerCity;
    private boolean specialProvince;
    private boolean specialCity;
    private boolean setWaterCount;

    public static String DIRECTCITYTAIL = "00";
    public static String CENTERCITYNO = "01";
    private static String specialProvinceNo = "10131";
    private static List<String> specialDistrictNoList = Arrays.asList(new String[]{"101201406","101081108"});
}

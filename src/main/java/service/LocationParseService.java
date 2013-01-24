package service;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-17
 * Time: 下午10:21
 * To change this template use File | Settings | File Templates.
 */
public class LocationParseService {
    public static LocationParseService getLocationParseServiceInstance(){
        if(locationParseService == null){
            locationParseService = new LocationParseService();
        }

        return locationParseService;
    }
    /**
     * Check if the city is governed by the part directly.
     * @param districtNo: the given city id.
     * @return: if the districtNo is from a direct city governed by party directly.
     */
    public boolean isDirectCity(String districtNo){
        return districtNo.startsWith("10101") ||
                districtNo.startsWith("10102") ||
                districtNo.startsWith("10103") ||
                districtNo.startsWith("10104");

    }

    public boolean isCenterCity(String districtNo){
        String realDistrictNo = "";
        if(isDirectCity(districtNo)){
            realDistrictNo = districtNo.substring(0,districtNo.length()-2);
        }

        return realDistrictNo.endsWith(CENTERCITYNO);
    }
    public static String DIRECTCITYTAIL = "00";
    public static String CENTERCITYNO = "01";
    /**
     * Avoid the user call the constructor directly.
     */
    private LocationParseService(){

    }
    private static LocationParseService locationParseService;
}

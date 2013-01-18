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
     * @param cityNo: the given city id.
     * @return
     */
    public boolean isDirectCity(String cityNo){
        return cityNo.startsWith("10101") ||
                cityNo.startsWith("10102") ||
                cityNo.startsWith("10103") ||
                cityNo.startsWith("10104");

    }

    public boolean isCity(String districtNo){
            return districtNo.equals("01");
    }

    public static String DIRECTCITYNO = "00";
    public static String NORMALCITYNO = "01";
    /**
     * Avoid the user call the constructor directly.
     */
    private LocationParseService(){

    }
    private static LocationParseService locationParseService;
}

package service;

import models.locationInfo.CityBean;
import models.locationInfo.CityDistrictBean;
import models.locationInfo.CountryBean;
import models.locationInfo.ProvinceBean;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-9
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
public class LoadCountryProvincesService {

    public static LoadCountryProvincesService getLoadCountryProvinceServiceInstance(){
        if (loadCountryProvincesService == null){
            loadCountryProvincesService = new LoadCountryProvincesService();
        }

        return loadCountryProvincesService;
    }

    public CountryBean InitCountryInfomation(String countryName){
        CountryBean countryBean = new CountryBean(countryName);
        List<ProvinceBean> provinceBeanList = countryBean.getProvinceBeanList();
        Map<String,String> countryNoNameMap = weatherFectchService.encodingCountryProvinces(countryBean.getCountryName());
        for(Map.Entry<String,String> entry : countryNoNameMap.entrySet()){
            String provinceNo = entry.getKey();
            String provinceName = entry.getValue();
            ProvinceBean provinceBean = initProvinceBean(provinceNo,provinceName);
            provinceBeanList.add(provinceBean);
        }

        return countryBean;
    }




    private ProvinceBean initProvinceBean(String provinceNo,String provinceName){
        ProvinceBean provinceBean = new ProvinceBean(provinceNo,provinceName);
        Map<String,String> provinceCitiesMap = weatherFectchService.encodingProvinceCities(provinceNo);
        List<CityBean> cityBeanList = provinceBean.getCities();
        for(Map.Entry<String,String> cityMapEntry : provinceCitiesMap.entrySet()){
            String cityNo = cityMapEntry.getKey();
            String cityName = cityMapEntry.getValue();
            CityBean currentCityBean = initCityBean(provinceNo,provinceNo+cityNo,cityName);

            cityBeanList.add(currentCityBean);

        }

        return provinceBean;
    }

    private CityBean initCityBean(String provinceNo,String cityNo,String cityName){
        CityBean cityBean = new CityBean(provinceNo,cityNo,cityName);
        Map<String,String> cityDistrict = weatherFectchService.encodingCityDistrict(cityNo);
        List<CityDistrictBean> cityDistrictBeanList = cityBean.getDistrictBeanList();
        for(Map.Entry<String,String> cityDistrictEntry : cityDistrict.entrySet()){

            String cityDistrictNo = cityNo + cityDistrictEntry.getKey();    //DistrictNo = ProvinceNo + CityNo + DistrictNo.

            if(locationParseService.isDirectCity(cityNo)){
                cityDistrictNo = provinceNo + cityDistrictEntry.getKey() + LocationParseService.DIRECTCITYNO;
            }
            String cityDistrictName = cityDistrictEntry.getValue();

            CityDistrictBean cityDistrictBean = new CityDistrictBean(cityNo,cityDistrictNo,cityDistrictName,locationParseService.isCity(cityDistrictEntry.getKey()));

            cityDistrictBeanList.add(cityDistrictBean);

        }

        return cityBean;
    }

    private LoadCountryProvincesService(){

    }

    private LocationParseService locationParseService = LocationParseService.getLocationParseServiceInstance();
    private WeatherFectchService weatherFectchService = WeatherFectchService.getServiceInstance();
    private static LoadCountryProvincesService loadCountryProvincesService;
}

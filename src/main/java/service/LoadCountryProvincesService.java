package service;

import models.dbmodels.WeatherInfoBean;
import models.locationInfo.CityBean;
import models.locationInfo.CityDistrictBean;
import models.locationInfo.CountryBean;
import models.locationInfo.ProvinceBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

    public void reloadCityDistrictInfo(String countryName){
        clearCityDistrictBeanFromDB();
        saveCityDistrictBeanToDB(countryName);

    }
    /**
     * Save all city district bean to db.
     * */
    private void saveCityDistrictBeanToDB(String countryName){
        if(countryBean == null){
            countryBean = initCountryInfomation(countryName);
            if(countryBean == null){
                countryBean = initCountryInfomation(DEFAULTCOUNTRY);
            }
        }

        List<CityDistrictBean> cityDistrictBeanList = countryBean.getCityDistrictList();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for(CityDistrictBean cityDistrictBean : cityDistrictBeanList){
            session.save(cityDistrictBean);
        }
        session.getTransaction().commit();
    }
    /**
     * Clear all city district bean from db.
     * */
    private void clearCityDistrictBeanFromDB(){
    }

    /**
     * Init the Country bean from website.
     * @param countryName: the name of the country.
     * @return:
     *
     *          The country bean which contains its name, its province beans which contains city beans.
     */
    private CountryBean initCountryInfomation(String countryName){
        countryBean = new CountryBean(countryName);
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
            CityBean currentCityBean = initCityBean(provinceNo,cityNo,cityName);

            cityBeanList.add(currentCityBean);
        }

        return provinceBean;
    }

    private CityBean initCityBean(String provinceNo,String cityNo,String cityName){
        CityBean cityBean = new CityBean(provinceNo,cityNo,cityName);
        cityNo = cityBean.getCityNo();
        Map<String,String> cityDistrict = weatherFectchService.encodingCityDistrict(cityNo);
        List<CityDistrictBean> cityDistrictBeanList = cityBean.getDistrictBeanList();
        for(Map.Entry<String,String> cityDistrictEntry : cityDistrict.entrySet()){

            String cityDistrictNo = cityDistrictEntry.getKey();
            String cityDistrictName = cityDistrictEntry.getValue();
            CityDistrictBean cityDistrictBean = new CityDistrictBean(provinceNo,cityNo,cityDistrictNo,cityDistrictName);

            cityDistrictBeanList.add(cityDistrictBean);
        }

        return cityBean;
    }

    private LoadCountryProvincesService(){

    }

    public static final String DEFAULTCOUNTRY = "china";

    private CountryBean countryBean;
    private WeatherFectchService weatherFectchService = WeatherFectchService.getServiceInstance();
    private static LoadCountryProvincesService loadCountryProvincesService;
    private SessionFactory sessionFactory = SessionFactoryService.getSessionFactory();


}

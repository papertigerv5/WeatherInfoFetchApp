package service;

import models.locationInfo.CityDistrictBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-2-24
 * Time: 上午10:57
 * To change this template use File | Settings | File Templates.
 */
public class CityDistrictBeanService {

    public static CityDistrictBeanService getCityDistrictBeanServiceInstance(){
        if(cityDistrictBeanService == null){
            cityDistrictBeanService = new CityDistrictBeanService();
        }

        return cityDistrictBeanService;
    }

    public List<CityDistrictBean> findAllCityDistrictBeans(){
        String hsql = "FROM CityDistrictBean";
        return session.createQuery(hsql).list();
    }


    public void saveCityDistrictBean(CityDistrictBean cityDistrictBean){
        session.beginTransaction();
        session.save(cityDistrictBean);
        session.getTransaction().commit();
    }

    public void batchSaveCityDistrictBeans(List<CityDistrictBean> cityDistrictBeanList){
        session.beginTransaction();
        for(CityDistrictBean cityDistrictBean : cityDistrictBeanList){
            session.save(cityDistrictBean);
        }
        session.getTransaction().commit();
    }

    private CityDistrictBeanService(){

    }
    private SessionFactory sessionFactory = SessionFactoryService.getSessionFactory();
    private Session session = sessionFactory.openSession();
    private static CityDistrictBeanService cityDistrictBeanService;
}

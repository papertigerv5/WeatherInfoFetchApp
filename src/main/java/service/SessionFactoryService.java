package service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-23
 * Time: 下午11:45
 * To change this template use File | Settings | File Templates.
 */
public class SessionFactoryService {

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        }

        return sessionFactory;
    }
    private static SessionFactory sessionFactory;
}

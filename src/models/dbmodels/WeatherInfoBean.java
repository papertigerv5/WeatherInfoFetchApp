package models.dbmodels;

import models.innerstorage.InTimeWeatherInfo;
import models.innerstorage.StaticWeatherInfoBean;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-17
 * Time: 下午8:29
 * To change this template use File | Settings | File Templates.
 */
public class WeatherInfoBean {

    public WeatherInfoBean(InTimeWeatherInfo inTimeWeatherInfo, StaticWeatherInfoBean staticWeatherInfoBean) {
        this.inTimeWeatherInfo = inTimeWeatherInfo;
        this.staticWeatherInfoBean = staticWeatherInfoBean;
    }

    public InTimeWeatherInfo getInTimeWeatherInfo() {
        return inTimeWeatherInfo;
    }

    public StaticWeatherInfoBean getStaticWeatherInfoBean() {
        return staticWeatherInfoBean;
    }

    @Override
    public boolean equals(Object o) {
        WeatherInfoBean weatherInfoBean = (WeatherInfoBean)o;
        return inTimeWeatherInfo.equals(weatherInfoBean.getInTimeWeatherInfo());
    }

    private InTimeWeatherInfo inTimeWeatherInfo;
    private StaticWeatherInfoBean staticWeatherInfoBean;
}

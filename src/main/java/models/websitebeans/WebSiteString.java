package models.websitebeans;

import models.locationInfo.CityDistrictBean;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-25
 * Time: 下午12:46
 * To change this template use File | Settings | File Templates.
 */
public class WebSiteString {

    public WebSiteString() {
        this.endFlag = true;
    }

    public WebSiteString(String httpContentString, String httpXmlString,String xmlContentString,CityDistrictBean cityDistrictBean) {
        this.httpContentString = httpContentString;
        this.httpXmlString = httpXmlString;
        this.xmlContentString = xmlContentString;
        this.cityDistrictBean = cityDistrictBean;
        this.endFlag = false;
        if(!xmlContentString.startsWith(XMLHEADER)){
            this.xmlError = true;
        }
        if(!httpXmlString.startsWith("{")){
            this.xmlFormatError = true;
        }
    }

    public CityDistrictBean getCityDistrictBean() {
        return cityDistrictBean;
    }

    public String getHttpContentString() {
        return httpContentString;
    }

    public String getHttpXmlString() {
        return httpXmlString;
    }

    public String getXmlContentString() {
        return xmlContentString;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }

    public boolean isEndFlag() {
        return endFlag;
    }

    public boolean isXmlError() {
        return xmlError;
    }

    public boolean isXmlFormatError() {
        return xmlFormatError;
    }

    public void setXmlFormatError(boolean xmlFormatError) {
        this.xmlFormatError = xmlFormatError;
    }

    private CityDistrictBean cityDistrictBean;
    private String httpContentString;
    private String httpXmlString;
    private String xmlContentString;
    private boolean endFlag;
    private boolean xmlError;
    private boolean xmlFormatError;

    private static final String XMLHEADER = "<?xml version=\"1.0\"?>";
}

package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-11
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class URLInfoService {

    public static URLInfoService getURLInfoServiceInstance(){
        if(service == null){
            service = new URLInfoService();
        }
        return service;
    }

    public String fetchInfoFromUrl(String urlName){
        StringBuilder sbr = new StringBuilder();
        try {
            URL url = new URL(urlName);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while((line=inputStream.readLine())!= null){
                sbr.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return sbr.toString();
    }

    public static URLInfoService service;

    private URLInfoService(){

    }
}

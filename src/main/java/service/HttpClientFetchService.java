package service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-12
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientFetchService {

    public static HttpClientFetchService getHttpClientFetchServiceInstance(){
        if(httpClientFetchService == null){
            httpClientFetchService = new HttpClientFetchService();
        }

        return httpClientFetchService;
    }

    public String getDWRString(String wholeUrl){
        String endUrl = wholeUrl.substring(wholeUrl.lastIndexOf(STRINGSLASH)+1);
        String methodName = endUrl.substring(endUrl.indexOf(STRINGDOT),endUrl.lastIndexOf(STRINGDOT));
        String scriptName = endUrl.substring(0,endUrl.indexOf(STRINGDOT));

        //Fetch whole string.
        return getDWRString(endUrl,scriptName,methodName);
    }

    private String getDWRString(String url,String scriptName,String methodName){
        HttpPost postMethod = new HttpPost(url);
        StringBuilder sbr = new StringBuilder();
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("callCount", "1"));
        formparams.add(new BasicNameValuePair("page", "/index.html"));
        formparams.add(new BasicNameValuePair("httpSessionId", ""));
        formparams.add(new BasicNameValuePair("scriptSessionId", "C19BDD2E8FC1F7026ED31AABD839D581169"));
        formparams.add(new BasicNameValuePair("c0-scriptName", scriptName));
        formparams.add(new BasicNameValuePair("c0-methodName", methodName));
        formparams.add(new BasicNameValuePair("c0-id", "0"));
        formparams.add(new BasicNameValuePair("batchId", "3"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            postMethod.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(postMethod);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = null;
            while((line = bufferedReader.readLine())!=null){
                sbr.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException ioException){
            ioException.printStackTrace();
        }

        return sbr.toString();
    }

    private HttpClientFetchService(){

    }



    //Instance.
    private HttpClient httpClient = new DefaultHttpClient();


    //static properties.
    private static final String STRINGSLASH = "/";
    private static final String STRINGDOT = ".";


    //services.
    private static HttpClientFetchService httpClientFetchService;
}

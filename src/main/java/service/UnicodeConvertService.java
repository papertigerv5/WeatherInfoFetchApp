package service;

import org.json.JSONException;
import org.json.JSONTokener;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-16
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 */
public class UnicodeConvertService {

    public static UnicodeConvertService getUnicodeConvertServiceInstance(){
        if(unicodeConvertService == null){
            unicodeConvertService = new UnicodeConvertService();
        }

        return unicodeConvertService;
    }
    public String convertUnicodeStringToChinese(String unicodeString){
        String jsonFormatValue = "'" + unicodeString + "'";
        try {
            return new JSONTokener(jsonFormatValue).nextValue().toString();
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    private UnicodeConvertService(){

    }
    private static UnicodeConvertService unicodeConvertService;
}

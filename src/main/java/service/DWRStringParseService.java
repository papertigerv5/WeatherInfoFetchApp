package service;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-3-16
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
public class DWRStringParseService {



    public static DWRStringParseService getDWRStringParseServiceInstance(){
        if(dwrStringParseService == null){
            dwrStringParseService = new DWRStringParseService();
        }

        return dwrStringParseService;
    }


    public String getHtmlStringByDwrString(String dwrString){
        return dwrString.substring(dwrString.indexOf(BEGINTAG),dwrString.lastIndexOf(ENDTAG)+1);
    }


    private DWRStringParseService(){}

    //properties.
    private static final String BEGINTAG = "<";
    private static final String ENDTAG = ">";
    //static service instance.
    private static DWRStringParseService dwrStringParseService;
}

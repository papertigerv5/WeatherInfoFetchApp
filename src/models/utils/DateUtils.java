package models.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yifan
 * Date: 13-1-17
 * Time: 下午8:17
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    public static String getDateFormat(Date date){
        return dateFormat.format(date);
    }

    public static String getTimeFormat(Date date){
        return timeFormat.format(date);
    }

    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
}

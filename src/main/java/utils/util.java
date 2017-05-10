package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Niki on 2017-05-10.
 *
 * @author Niki
 */
public class util {

    public static String getToday() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        System.out.println(df.format(Calendar.getInstance().getTime()));
        return df.format(Calendar.getInstance().getTime());
    }
}

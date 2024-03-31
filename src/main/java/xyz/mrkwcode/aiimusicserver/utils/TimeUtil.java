package xyz.mrkwcode.aiimusicserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static final String TIME_FULL_SPRIT = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date, String formartStr) {
        String strDate = null;
        if(date != null && formartStr != null && !formartStr.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
            strDate = sdf.format(date);
        }
        return strDate;
    }
}

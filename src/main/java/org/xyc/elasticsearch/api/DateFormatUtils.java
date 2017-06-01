package org.xyc.elasticsearch.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CCC on 2016/6/19.
 */
public class DateFormatUtils {

    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static final Map<String, ThreadLocal<SimpleDateFormat>> format_map = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static SimpleDateFormat getDateFormat(final String str) {
        ThreadLocal<SimpleDateFormat> tl = format_map.get(str);
        synchronized (format_map) {
            if (tl == null) {
                tl = new ThreadLocal<SimpleDateFormat>() {
                    @Override
                    protected SimpleDateFormat initialValue() {
                        return new SimpleDateFormat(str);
                    }
                };
                format_map.put(str, tl);
            }
        }
        return tl.get();
    }

    public static String format(String str, Date date) {
        return getDateFormat(str).format(date);
    }

    public static String formatDefault(Date date) {
        return format(DATE_FORMAT_DEFAULT, date);
    }

    public static Date parse(String str, String date) {
        try {
            return getDateFormat(str).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date parseDefault(String date) {
        return parse(DATE_FORMAT_DEFAULT, date);
    }
}

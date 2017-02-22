package cn.yj.market.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 */
public final class DateUtil {
    private static Logger                    log               = LoggerFactory.getLogger(DateUtil.class);
    private static final String           DATE_PATTERN      = "yyyy-MM-dd";
    private static final String           DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat DEFAULT_DATE      = new SimpleDateFormat(DATE_PATTERN);
    private static final SimpleDateFormat DEFAULT_DATE_TIME = new SimpleDateFormat(DATE_TIME_PATTERN);

    private DateUtil() {
    }

    public static String getDatePattern() {
        return DATE_PATTERN;
    }

    public static String getDateTimePattern() {
        return DATE_TIME_PATTERN;
    }

    public static SimpleDateFormat getDFDate() {
        return DEFAULT_DATE;
    }

    public static SimpleDateFormat getDFDateTime() {
        return DEFAULT_DATE_TIME;
    }

    public static String getDateTime(Date aDate) {
        String returnValue = null;
        if (aDate != null) {
            returnValue = getDFDateTime().format(aDate);
        }
        return returnValue;
    }

    public static String getDate(Date aDate) {
        String returnValue = null;
        if (aDate != null) {
            returnValue = getDFDate().format(aDate);
        }
        return returnValue;
    }

    public static Date strToDate(String strDate) {
        Date date = null;
        try {
            date = getDFDate().parse(strDate);
        } catch (Exception pe) {
            log.warn("converting '" + strDate + "' to date with mask '" + DATE_PATTERN + "' is error!");
        } finally {
            return date;
        }
    }

    public static Date strToDateTime(String strDateTime) {
        Date date = null;
        try {
            date = getDFDateTime().parse(strDateTime);
        } catch (Exception pe) {
            log.warn("converting '" + strDateTime + "' to date with mask '" + DATE_TIME_PATTERN + "' is error!");
        } finally {
            return date;
        }
    }
    
    public static String getForBitYear(Date val) {
        return getDate(val).substring(0, 4);
    }

    public static String getTwoBitMonth(Date val) {
        return getDate(val).substring(5, 7);
    }

}

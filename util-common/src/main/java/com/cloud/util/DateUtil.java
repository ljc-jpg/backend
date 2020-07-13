package com.cloud.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
    }

    /**
     * @Author zhuz
     * @Description date转LocalDateTime
     * @Date 17:02 2020/7/6
     * @Param [date]
     **/
    public static LocalDateTime dateToLocalDateTime(Date date) {
        LocalDateTime localDateTime = null;
        try {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            localDateTime = instant.atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            logger.error("dateToLocalDateTime:" + e);
        }
        return localDateTime;
    }

    /**
     * @Author zhuz
     * @Description LocalDateTime转Date
     * @Date 17:03 2020/7/6
     * @Param [localDateTime]
     **/
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        localDateTime.getMonth();
        return Date.from(zdt.toInstant());
    }

    /**
     * @Author zhuz
     * @Description string转LocalDateTime  timeFormat格式
     * @Date 10:27 2020/7/7
     * @Param [date]
     **/
    public static LocalDateTime parseStrToLocalDateTime(String date, String timeFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(timeFormat);
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(date, df);
        } catch (Exception e) {
            logger.error("parseStrToDate:" + e);
        }
        return dateTime;
    }

    /**
     * @Author zhuz
     * @Description LocalDateTime转string timeFormat格式
     * @Date 11:15 2020/7/7
     * @Param [localDateTime, timeFormat]
     **/
    public static String formatLocalDateTimeToStr(LocalDateTime localDateTime, String timeFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(timeFormat);
        String localTime = null;
        try {
            localTime = df.format(localDateTime);
        } catch (Exception e) {
            logger.error("parseLocalDateTimeToStr:" + e);
        }
        return localTime;
    }

    /**
     * @Author zhuz
     * @Description 获取日期当前月
     * @Date 10:12 2020/7/7
     * @Param [localDateTime]
     **/
    public static Integer getMonth(LocalDateTime localDateTime) {
        return localDateTime.getMonthValue();
    }

    /********************************************  工具常量 *******************************************************/

    /**
     * 日期格式，年份，例如：2004，2008
     */
    public static String DATE_FORMAT_YYYY = "yyyy";

    /**
     * 日期格式，年份和月份，例如：200707
     */
    public static String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 日期格式，年份和月份，例如：2008-08
     */
    public static String DATE_FORMAT_YYYY_MM = "yyyy-MM";

    /**
     * 日期格式，年月日，例如：080808
     */
    public static String DATE_FORMAT_YYMMDD = "yyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：08-08-08
     */
    public static String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";

    /**
     * 日期格式，年月日，例如：20050630
     */
    public static String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式，年月日，用横杠分开，例如：2006-12-25
     */
    public static String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式，年月日，例如：2016.10.05
     */
    public static String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";

    /**
     * 日期格式，年月日，例如：2016年10月05日
     */
    public static String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";

    /**
     * 日期格式，年月日时分，例如：200506301210，200808081210
     */
    public static String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";

    /**
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08
     */
    public static String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";

    /**
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08
     */
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    /**
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
     */
    public static String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123
     */
    public static String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";

    /**
     * 日期格式，月日时分，例如：10-05 12:00
     */
    public static String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";


}

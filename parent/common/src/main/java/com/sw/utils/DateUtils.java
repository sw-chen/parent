package com.sw.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 日期工具类
 *
 * @date 2017-3-3 14:48:20
 */
public class DateUtils {

    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<>();

    public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond

    // 取值范围：就是临界点包不包含等于
    public final static int LEFT_OPEN_RIGHT_OPEN = 1;
    public final static int LEFT_CLOSE_RIGHT_OPEN = 2;
    public final static int LEFT_OPEN_RIGHT_CLOSE = 3;
    public final static int LEFT_CLOSE_RIGHT_CLOSE = 4;
    /**
     * 比较日期的模式 --只比较日期，不比较时间
     */
    public final static int COMP_MODEL_DATE = 1;
    /**
     * 比较日期的模式 --只比较时间，不比较日期
     */
    public final static int COMP_MODEL_TIME = 2;
    /**
     * 比较日期的模式 --比较日期，也比较时间
     */
    public final static int COMP_MODEL_DATETIME = 3;

    /**
     * 要用到的DATE Format的定义
     */
    public static String DATE_FORMAT_DATE_ONLY = "yyyy-MM-dd"; // 年/月/日
    public static String DATE_FORMAT_DATE_HOUR = "yyyy-MM-dd HH:mm";
    public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static String LONG_DATE_FORMAT_SSS_STRING = "yyyy-MM-dd HH:mm:ss.SSS";
    public static String DATE_MONTH_DATE = "yyyyMMdd";
    public static String HMS_FORMAT_STRING = "HH:mm:ss";
    public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";

    /**
     * 获取当前线程的simpleDateFormat
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = simpleDateFormatThreadLocal.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormatThreadLocal.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }


    /**
     * 根据单位字段比较两个日期
     *
     * @param date      日期1
     * @param otherDate 日期2
     * @param withUnit  单位字段，从Calendar field取值
     * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public static int compareDate(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);

        switch (withUnit) {
            case Calendar.YEAR:
                dateCal.clear(Calendar.MONTH);
                otherDateCal.clear(Calendar.MONTH);
            case Calendar.MONTH:
                dateCal.set(Calendar.DATE, 1);
                otherDateCal.set(Calendar.DATE, 1);
            case Calendar.DATE:
                dateCal.set(Calendar.HOUR_OF_DAY, 0);
                otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
                dateCal.clear(Calendar.MINUTE);
                otherDateCal.clear(Calendar.MINUTE);
            case Calendar.MINUTE:
                dateCal.clear(Calendar.SECOND);
                otherDateCal.clear(Calendar.SECOND);
            case Calendar.SECOND:
                dateCal.clear(Calendar.MILLISECOND);
                otherDateCal.clear(Calendar.MILLISECOND);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }

    /**
     * 根据单位字段比较两个时间
     *
     * @param date      时间1
     * @param otherDate 时间2
     * @param withUnit  单位字段，从Calendar field取值
     * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public static int compareTime(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);

        dateCal.clear(Calendar.YEAR);
        dateCal.clear(Calendar.MONTH);
        dateCal.set(Calendar.DATE, 1);
        otherDateCal.clear(Calendar.YEAR);
        otherDateCal.clear(Calendar.MONTH);
        otherDateCal.set(Calendar.DATE, 1);
        switch (withUnit) {
            case Calendar.HOUR:
                dateCal.clear(Calendar.MINUTE);
                otherDateCal.clear(Calendar.MINUTE);
            case Calendar.MINUTE:
                dateCal.clear(Calendar.SECOND);
                otherDateCal.clear(Calendar.SECOND);
            case Calendar.SECOND:
                dateCal.clear(Calendar.MILLISECOND);
                otherDateCal.clear(Calendar.MILLISECOND);
            case Calendar.MILLISECOND:
                break;
            default:
                throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }

    /**
     * 获得当前的日期毫秒
     *
     * @return
     */
    public static long nowTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获得当前的时间戳
     *
     * @return
     */
    public static Timestamp nowTimeStamp() {
        return new Timestamp(nowTimeMillis());
    }

    /**
     * yyyy-MM-dd 当前日期
     */
    public static String getReqDate() {
        return getSimpleDateFormat(DATE_MONTH_DATE).format(new Date());
    }

    /**
     * yyyy-MM-dd 传入日期
     *
     * @param date
     * @return
     */
    public static String getReqDate(Date date) {
        return getSimpleDateFormat(DATE_FORMAT_DATE_ONLY).format(date);
    }

    /**
     * yyyyMMdd 传入日期
     *
     * @param date
     * @return
     */
    public static String getReqDateyyyyMMdd(Date date) {
        return getSimpleDateFormat(DATE_MONTH_DATE).format(date);
    }

    /**
     * yyyy-MM-dd 传入的时间戳
     *
     * @param tmp
     * @return
     */
    public static String TimestampToDateStr(Timestamp tmp) {
        return getSimpleDateFormat(DATE_FORMAT_DATE_ONLY).format(tmp);
    }

    /**
     * HH:mm:ss 当前时间
     *
     * @return
     */
    public static String getReqTime() {
        return getSimpleDateFormat(HMS_FORMAT_STRING).format(new Date());
    }

    /**
     * 得到时间戳格式字串
     *
     * @param date
     * @return
     */
    public static String getTimeStampStr(Date date) {
        return getSimpleDateFormat(DATE_FORMAT_DATETIME).format(date);
    }

    /**
     * 得到长日期格式字串
     *
     * @return
     */
    public static String getLongDateStr() {
        return getSimpleDateFormat(DATE_FORMAT_DATETIME).format(new Date());
    }

    public static String getLongDateStr(Timestamp time) {
        return getSimpleDateFormat(DATE_FORMAT_DATETIME).format(time);
    }

    /**
     * 得到短日期格式字串
     *
     * @param date
     * @return
     */
    public static String getShortDateStr(Date date) {
        return getSimpleDateFormat(DATE_FORMAT_DATE_ONLY).format(date);
    }

    public static String getShortDateStr() {
        return getSimpleDateFormat(DATE_FORMAT_DATE_ONLY).format(new Date());
    }

    /**
     * 计算 second 秒后的时间
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ;
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 计算 minute 分钟后的时间
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 计算 hour 小时后的时间
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static void clearTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    public static long add(Date date, TimeUnit unit, int duration) {
        return date.getTime() + unit.toMillis(duration);
    }

    public static boolean betweenInHour(Date time, int min, int max) {
        return betweenInHour(time.getTime(), min, max);
    }

    public static boolean betweenInHour(long timeInMillis, int min, int max) {
        return betweenInMillis(timeInMillis, TimeUnit.HOURS.toMillis(min), TimeUnit.HOURS.toMillis(max));
    }

    public static boolean betweenInMillis(Date time, int min, int max) {
        return betweenInMillis(time.getTime(), min, max);
    }

    public static boolean betweenInMillis(long timeInMillis, int min, int max) {
        return betweenInMillis(timeInMillis, TimeUnit.MINUTES.toMillis(min), TimeUnit.MINUTES.toMillis(max));
    }

    public static boolean betweenInMillis(long timeInMillis, long min, long max) {
        long span = System.currentTimeMillis() - timeInMillis;
        return span >= min && span <= max;
    }

    /**
     * 得到day的起始时间点。
     *
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到day的终止时间点.
     *
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 计算 day 天后的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }


    /**
     * 得到当月起始时间
     *
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getSeasonStart(Date date) {
        return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getSeasonEnd(Date date) {
        return getDayEnd(getLastDateOfMonth(getSeasonDate(date)[2]));
    }


    /**
     * 得到month的终止时间点.
     *
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 得到当前周起始时间
     *
     * @param date
     * @return
     */
    public static Date getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.WEEK_OF_YEAR);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当前周截止时间
     *
     * @param date
     * @return
     */
    public static Date getWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.WEEK_OF_YEAR);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, 8 - firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当前年起始时间
     *
     * @param date
     * @return
     */
    public static Date getYearStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到当前年最后一天
     *
     * @param date
     * @return
     */
    public static Date getYearEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 365 * year);
        return calendar.getTime();
    }

    public static Timestamp strToTimestamp(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    public static Timestamp strToTimestamp(Date date) {
        return Timestamp.valueOf(getSimpleDateFormat(DATE_FORMAT_DATETIME).format(date));
    }

    public static Timestamp getCurTimestamp() {
        return Timestamp.valueOf(getSimpleDateFormat(DATE_FORMAT_DATETIME).format(new Date()));
    }

    /**
     * 取得两个日期之间的日数
     *
     * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
     */
    public static long daysBetween(Timestamp t1, Timestamp t2) {
        return (t2.getTime() - t1.getTime()) / DAY_MILLI;
    }

    /**
     * 返回java.sql.Timestamp型的SYSDATE
     *
     * @return java.sql.Timestamp型的SYSDATE
     * @history
     * @since 1.0
     */
    public static Timestamp getSysDateTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
     *
     * @param sDate Date string
     * @return
     * @history
     * @since 1.0
     */
    public static Timestamp toSqlTimestamp(String sDate) {
        if (sDate == null) {
            return null;
        }
        if (sDate.length() != DateUtils.DATE_FORMAT_DATE_ONLY.length()
                && sDate.length() != DateUtils.DATE_FORMAT_DATETIME.length()) {
            return null;
        }
        return toSqlTimestamp(sDate,
                sDate.length() == DateUtils.DATE_FORMAT_DATE_ONLY.length()
                        ? DateUtils.DATE_FORMAT_DATE_ONLY
                        : DateUtils.DATE_FORMAT_DATETIME);

    }

    public static Date toDate(String sDate) {
        if (sDate == null) {
            return null;
        }
        if (sDate.length() != DateUtils.DATE_FORMAT_DATE_ONLY.length()
                && sDate.length() != DateUtils.DATE_FORMAT_DATETIME.length()) {
            return null;
        }
        return toSqlTimestamp(sDate,
                sDate.length() == DateUtils.DATE_FORMAT_DATE_ONLY.length()
                        ? DateUtils.DATE_FORMAT_DATE_ONLY
                        : DateUtils.DATE_FORMAT_DATETIME);

    }

    public static Date toDateHour(String sDate) {
        if (sDate == null) {
            return null;
        }
        if (sDate.length() != DateUtils.DATE_FORMAT_DATE_HOUR.length()) {
            return null;
        }
        return toSqlTimestamp(sDate, DateUtils.DATE_FORMAT_DATE_HOUR);
    }

    /**
     * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
     *
     * @param sDate Date string
     * @param sFmt  Date format DATE_FORMAT_DATE_ONLY/DATE_FORMAT_DATETIME
     * @return
     * @history
     * @since 1.0
     */
    public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
        String temp = null;
        if (sDate == null || sFmt == null) {
            return null;
        }
        if (sDate.length() != sFmt.length()) {
            return null;
        }
        if (sFmt.equals(DateUtils.DATE_FORMAT_DATETIME)) {
            temp = sDate.replace('/', '-');
            temp = temp + ".000000000";
        } else if (sFmt.equals(DateUtils.DATE_FORMAT_DATE_ONLY)) {
            temp = sDate.replace('/', '-');
            temp = temp + " 00:00:00.000000000";
        } else if (sFmt.equals(DateUtils.DATE_FORMAT_DATE_HOUR)) {
            temp = sDate.replace('/', '-');
            temp = temp + ":00.000000000";
        } else {
            return null;
        }
        return Timestamp.valueOf(temp);
    }


    /**
     * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
     *
     * @return 系统日期时间
     * @history
     * @since 1.0
     */
    public static String getSysDateTimeString() {
        return toString(new Date(System.currentTimeMillis()), getSimpleDateFormat(DateUtils.DATE_FORMAT_DATETIME));
    }

    /**
     * 根据指定的Format转化java.util.Date到String
     *
     * @param dt   java.util.Date instance
     * @param sFmt Date format , DATE_FORMAT_DATE_ONLY or DATE_FORMAT_DATETIME
     * @return
     * @history
     * @since 1.0
     */
    public static String toString(Date dt, String sFmt) {
        if (dt == null || sFmt == null || "".equals(sFmt)) {
            return "";
        }
        return toString(dt, new SimpleDateFormat(sFmt));
    }

    /**
     * 利用指定SimpleDateFormat instance转换java.util.Date到String
     *
     * @param dt        java.util.Date instance
     * @param formatter SimpleDateFormat Instance
     * @return
     * @history
     * @since 1.0
     */
    private static String toString(Date dt, SimpleDateFormat formatter) {
        return formatter.format(dt).toString();
    }

    /**
     * 转换java.sql.Timestamp到String，格式为YYYY/MM/DD HH24:MI
     *
     * @param dt java.sql.Timestamp instance
     * @return
     * @history
     * @since 1.0
     */
    public static String toSqlTimestampString2(Timestamp dt) {
        if (dt == null) {
            return null;
        }
        String temp = toSqlTimestampString(dt, DateUtils.DATE_FORMAT_DATETIME);
        return temp.substring(0, 16);
    }

    public static String toString(Timestamp dt) {
        return dt == null ? "" : toSqlTimestampString2(dt);
    }

    /**
     * 根据指定的格式转换java.sql.Timestamp到String
     *
     * @param dt   java.sql.Timestamp instance
     * @param sFmt Date 格式，DATE_FORMAT_DATE_ONLY/DATE_FORMAT_DATETIME/
     *             DATE_FORMAT_SESSION
     * @return
     * @history
     * @since 1.0
     */
    public static String toSqlTimestampString(Timestamp dt, String sFmt) {
        String temp = null;
        String out = null;
        if (dt == null || sFmt == null) {
            return null;
        }
        temp = dt.toString();
        if (sFmt.equals(DateUtils.DATE_FORMAT_DATETIME) || // "YYYY/MM/DD
// HH24:MI:SS"
                sFmt.equals(DateUtils.DATE_FORMAT_DATE_ONLY)) { // YYYY/MM/DD
            temp = temp.substring(0, sFmt.length());
            out = temp.replace('/', '-');
// }else if( sFmt.equals (DateUtils.DATE_FORMAT_SESSION ) ){
// //Session
// out =
// temp.substring(0,4)+temp.substring(5,7)+temp.substring(8,10);
// out += temp.substring(12,14) + temp.substring(15,17);
        }
        return out;
    }

    // 得到当前日期的星期
    public static int getWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w;
    }

    //某一特定日期转星期几
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * Timestamp 格式转换成yyyy-MM-dd timestampToSql(Timestamp 格式转换成yyyy-MM-dd)
     *
     * @param timestamp 时间
     * @return createTimeStr yyyy-MM-dd 时间
     * @Exception 异常对象
     * @since V1.0
     */
    public static String timestampToStringYMD(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATE_ONLY);
        String createTimeStr = sdf.format(timestamp);
        return createTimeStr;
    }

    /**
     * 判断一个时间是否在某个时间区间内
     *
     * @param now   目标时间
     * @param start 时间区间开始
     * @param end   时间区间结束
     * @param model 区间模式
     * @return 是否在区间内
     */
    public static boolean isBetween(Date now, Date start, Date end, int model) {
        return isBetween(now, start, end, model, LEFT_OPEN_RIGHT_OPEN);
    }

    /**
     * 判断时间是否在制定的时间段之类
     *
     * @param date       需要判断的时间
     * @param start      时间段的起始时间
     * @param end        时间段的截止时间
     * @param interModel 区间的模式
     * @param compModel  比较的模式
     * @return
     */
    public static boolean isBetween(Date date, Date start, Date end, int interModel, int compModel) {
        if (date == null || start == null || end == null) {
            throw new IllegalArgumentException("日期不能为空");
        }
        SimpleDateFormat format = null;
        switch (compModel) {
            case COMP_MODEL_DATE: {
                format = new SimpleDateFormat("yyyyMMdd");
                break;
            }
            case COMP_MODEL_TIME: {
                format = new SimpleDateFormat("HHmmss");
                break;
            }
            case COMP_MODEL_DATETIME: {
                format = new SimpleDateFormat("yyyyMMddHHmmss");
                break;
            }
            default: {
                throw new IllegalArgumentException(String.format("日期的比较模式[%d]有误", compModel));
            }
        }
        long dateNumber = Long.parseLong(format.format(date));
        long startNumber = Long.parseLong(format.format(start));
        long endNumber = Long.parseLong(format.format(end));
        switch (interModel) {
            case LEFT_OPEN_RIGHT_OPEN: {
                if (dateNumber <= startNumber || dateNumber >= endNumber) {
                    return false;
                } else {
                    return true;
                }
            }
            case LEFT_CLOSE_RIGHT_OPEN: {
                if (dateNumber < startNumber || dateNumber >= endNumber) {
                    return false;
                } else {
                    return true;
                }
            }
            case LEFT_OPEN_RIGHT_CLOSE: {
                if (dateNumber <= startNumber || dateNumber > endNumber) {
                    return false;
                } else {
                    return true;
                }
            }
            case LEFT_CLOSE_RIGHT_CLOSE: {
                if (dateNumber < startNumber || dateNumber > endNumber) {
                    return false;
                } else {
                    return true;
                }
            }
            default: {
                throw new IllegalArgumentException(String.format("日期的区间模式[%d]有误", interModel));
            }
        }
    }


    /**
     * 取得月天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {
        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getMonthOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 字符串转date
     *
     * @param dateString
     * @return
     */
    public static Date StringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATE_HOUR);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串转date  精确到s
     *
     * @param dateString
     * @return
     */
    public static Date StringToDateTime(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATETIME);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 判断输入日期是一个星期中的第几天(星期天为一个星期第一天)
     *
     * @param date
     * @return
     */
    public static int getWeekIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 当前时间的前几天，并且以例如2013/12/09 00:00:00 形式输出
     */
    public static Date subDays(int days) {
        Date date = addDay(new Date(), -days);
        String dateStr = getReqDate(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制 如：开始时间和结束时间，不能超出距离当前时间90天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间按
     * @param interval  间隔数
     * @param dateUnit  单位(如：月，日),参照Calendar的时间单位
     * @return
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(dateUnit, interval * (-1));
        Date curDate = getDayStart(cal.getTime());
        if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间按
     * @param interval  间隔数
     * @return
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, interval * (-1));
        Date curDate = getDayStart(cal.getTime());
        if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
            return true;
        }
        return false;
    }


    /**
     * 传入时间字符串及时间格式，返回对应的Date对象
     *
     * @param src     时间字符串
     * @param pattern 时间格式
     * @return Date
     */
    public static Date getDateFromString(String src, String pattern) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        try {
            return f.parse(src);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 取季度
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getQuarter(Date date) {
        if (date.getMonth() == 0 || date.getMonth() == 1 || date.getMonth() == 2) {
            return 1;
        } else if (date.getMonth() == 3 || date.getMonth() == 4 || date.getMonth() == 5) {
            return 2;
        } else if (date.getMonth() == 6 || date.getMonth() == 7 || date.getMonth() == 8) {
            return 3;
        } else if (date.getMonth() == 9 || date.getMonth() == 10 || date.getMonth() == 11) {
            return 4;
        } else {
            return 0;

        }
    }

    /**
     * 取得通用日期时间格式字符串
     *
     * @param date
     * @return String
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取当日的日期格式串
     *
     * @param
     * @return String
     */
    public static String today() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取当前时间格式串
     *
     * @param
     * @return String
     */
    public static String currentTime() {
        return formatDate(new Date(), "yyyyMMddhhmmssSSS");
    }

    /**
     * 取得指定日期格式的字符串
     *
     * @param date
     * @return String
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 获取昨日的日期格式串
     *
     * @return Date
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return formatDate(calendar.getTime(), "yyyyMMdd");
    }

    public static String getAppointDay(Date date, int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, x);
        return formatDate(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 判断当前时间是否在一定的时间范围内
     *
     * @param startTime
     * @return boolean
     */
    public static boolean isInBetweenTimes(String startTime, String endTime) {
        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(nowTime);
        if (time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符转日期
     *
     * @param dateStr
     * @return
     */
    public static Date getDateByStr(String dateStr) {
        SimpleDateFormat formatter = null;
        if (dateStr == null) {
            return null;
        } else if (dateStr.length() == 10) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        } else if (dateStr.length() == 16) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (dateStr.length() == 19) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (dateStr.length() > 19) {
            dateStr = dateStr.substring(0, 19);
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            return null;
        }
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 根据传入的数字，输出相比现在days天的数据
     *
     * @param days
     * @return Date
     */
    public static Date getDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 日期最大时间
     *
     * @param dt
     * @return
     */
    public static Date getMaxTime(Date dt) {

        Date dt1 = null;
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        dt1 = ca.getTime();
        dt1 = DateUtils.getMinTime(dt1);
        ca.setTime(dt1);
        ca.add(Calendar.SECOND, -1);
        dt1 = ca.getTime();
        return dt1;
    }

    /**
     * 日期最小时间
     *
     * @param dt
     * @return
     */
    public static Date getMinTime(Date dt) {
        Date dt1 = null;
        dt1 = DateUtils.getDateByStr(DateUtils.formatDate(dt, "yyyy-MM-dd"));
        return dt1;
    }

    /**
     * 月的最后一天
     *
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    /**
     * 月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 上月第一天
     *
     * @return
     */
    public static Date getPreviousMonthFirstDay() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        return getMinTime(lastDate.getTime());
    }

    /**
     * 上月最后一天
     *
     * @return
     */
    public static Date getPreviousMonthLastDay() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.DATE, -1);
        return getMinTime(lastDate.getTime());
    }

    /**
     * 两个日期相关天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDateDiff(String startDate, String endDate) {
        long diff = 0;
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

            diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime())
                    / (24 * 60 * 60 * 1000)
                    : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
        }
        return diff;
    }


    /*
     * 判断两个时间是不是在一个周中
     */
    public static boolean isSameWeekWithToday(Date date) {

        if (date == null) {
            return false;
        }

// 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);
        int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
// subYear==0,说明是同一年
        if (subYear == 0) {
            if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == 1 && dateCal.get(Calendar.MONTH) == 11 && todayCal.get(Calendar.MONTH) == 0) {
            if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == -1 && todayCal.get(Calendar.MONTH) == 11 && dateCal.get(Calendar.MONTH) == 0) {
            if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * getStrFormTime:
     *
     * @param form 格式时间
     * @param date 时间
     * @return
     */
    public static String getStrFormTime(String form, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(form);
        return sdf.format(date);
    }

    /**
     * 获取几天内日期 return 2014-5-4、2014-5-3
     */
    public static List<String> getLastDays(int countDay) {
        List<String> listDate = new ArrayList<String>();
        for (int i = 0; i < countDay; i++) {
            listDate.add(DateUtils.getReqDateyyyyMMdd(DateUtils.getDate(-i)));
        }
        return listDate;
    }

    /**
     * 对时间进行格式化
     *
     * @param date
     * @return
     */
    public static Date dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date value = new Date();

        try {
            value = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;

    }

    //获取本月第一天
    public static Date getCurMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //获取上月第一天
    public static Date getPreMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.AM_PM, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //获取某月最后一天
    public static Date getLastDayOfMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date stampToDate(Long s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        return date;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    //根据出生日期得出年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一 1
            }
        }
        if (age == 0)
            ++age;

        return age;
    }

    /**
     * 获取指定日期所在月份开始的时间戳
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getMonthBegin(String year, String month) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTime();
    }

    /**
     * 获取指定日期所在月份结束的时间戳
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getMonthEnd(String year, String month) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        //设置为当月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE, 59);
        //将秒至59
        c.set(Calendar.SECOND, 59);
        //将毫秒至999
        c.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return c.getTime();
    }

}
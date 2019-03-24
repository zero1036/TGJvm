package com.tg.date;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by linzc on 2017/6/19.
 */
public final class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    private static final String DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式
     */
    public static final String VIEW_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 构造器
     */
    private DateUtils() {
        /* set something here */
    }

    /**
     * 获取更改时区后的日期（UTC转为本地时间）
     *
     * @param date 日期
     * @return 日期
     */
    public static Date changeTimeZoneUTCToLocal(Date date) {
        return changeTimeZone(date, TimeZone.getTimeZone("GMT"), TimeZone.getTimeZone("Asia/Shanghai"));
    }

    /**
     * 获取更改时区后的日期
     *
     * @param date    日期
     * @param oldZone 旧时区对象
     * @param newZone 新时区对象
     * @return 日期
     */
    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        Date dateTmp = null;
        if (date != null) {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }

    /**
     * 获取日期的周是一年的第几周
     *
     * @param date 日期
     * @return 第几周
     * @throws IllegalArgumentException
     */
    public static int getWeekOfYear(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setFirstDayOfWeek(Calendar.MONDAY); //美国时间认为周日为周的起始，改为周一；但由于Dotnet系统大量使用以周日起算的算法，此处保持统一
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }
        return 0;
    }

    /**
     * 获取时间差
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param type      差值类型
     * @return 差值
     */
    public static long getTimeDifference(Date beginDate, Date endDate, int type) {
        if (beginDate.before(endDate)) {
            long between = endDate.getTime() - beginDate.getTime();

            long day = between / (24 * 60 * 60 * 1000);
            long hour = (between / (60 * 60 * 1000) - day * 24);
            long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            switch (type) {
                case 1:
                    return day;
                case 2:
                    return hour;
                case 3:
                    return min;
                case 4:
                    return s;
                case 5:
                    return ms;
                default:
                    return 0;
            }
        }
        return 0;
    }

    /**
     * 获取某天零点
     *
     * @param date 某天日期
     * @return
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }

    /**
     * 获取某天末点
     *
     * @param date 某天日期
     * @return
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return day.getTime();
    }

    /**
     * 获取某天所属月的第一天零点
     *
     * @param date 某天日期
     * @return Date
     */
    public static Date getStartTimeOfMonth(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.DAY_OF_MONTH, 1);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }

    /**
     * 获取某天所属月的最后一天末点
     *
     * @param date 某天日期
     * @return Date
     */
    public static Date getEndTimeOfMonth(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.set(Calendar.DAY_OF_MONTH, day.getActualMaximum(Calendar.DAY_OF_MONTH));
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return day.getTime();
    }

    /**
     * 获取某一周起始零点
     *
     * @param date 某天日期
     * @return 返回时间
     */
    public static Date getStartTimeOfWeek(Date date, boolean firstDayIsMonday) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);

        day.set(Calendar.DAY_OF_WEEK, 1);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }

    /**
     * 获取某一周结束的末点
     *
     * @param date 某天日期
     * @return 返回时间
     */
    public static Date getEndTimeOfWeek(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);

        day.set(Calendar.DAY_OF_WEEK, 7);
        day.set(Calendar.HOUR_OF_DAY, 23);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return day.getTime();
    }
}

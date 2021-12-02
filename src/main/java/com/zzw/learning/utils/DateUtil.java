package com.zzw.learning.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DateUtil {
    /**
     * 时间戳转换成日期格式字符串
     *
     * @param milliseconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String milliseconds, String format) {
        if (milliseconds == null || milliseconds.isEmpty() || milliseconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(milliseconds)));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * String转时间戳
     *
     * @return LocalDateTime
     */
    public static LocalDateTime string2LocalDateTime(String dateString, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, df);
        return localDateTime;
    }

    /**
     * String转时间戳
     *
     * @return LocalDateTime
     */
    public static LocalDate string2LocalDate(String dateString) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, fmt);
    }

    /**
     * LocalDateTime转long
     *
     * @return LocalDateTime
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDate localDateToDateTime(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        return instant.atZone(defaultZoneId).toLocalDate();
    }

    /**
     * 返回当前的日期
     *
     * @return
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前日期时间
     *
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 是否当天
     *
     * @param date
     * @return
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 计算两个时间点的天数差
     *
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即要计算的天数差
     */
    public static int dateDiff(LocalDateTime dt1, LocalDateTime dt2) {
        //获取第一个时间点的时间戳对应的秒数
        long t1 = dt1.toEpochSecond(ZoneOffset.ofHours(0));
        //获取第一个时间点在是1970年1月1日后的第几天
        long day1 = t1 / (60 * 60 * 24);
        //获取第二个时间点的时间戳对应的秒数
        long t2 = dt2.toEpochSecond(ZoneOffset.ofHours(0));
        //获取第二个时间点在是1970年1月1日后的第几天
        long day2 = t2 / (60 * 60 * 24);
        //返回两个时间点的天数差
        return (int) (day2 - day1);
    }

    /**
     * 获取两个时间点的月份差
     *
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即需求的月数差
     */
    public static int monthDiff(LocalDateTime dt1, LocalDateTime dt2) {
        //获取第一个时间点的月份
        int month1 = dt1.getMonthValue();
        //获取第一个时间点的年份
        int year1 = dt1.getYear();
        //获取第一个时间点的月份
        int month2 = dt2.getMonthValue();
        //获取第一个时间点的年份
        int year2 = dt2.getYear();
        //返回两个时间点的月数差
        return (year2 - year1) * 12 + (month2 - month1);
    }

    /**
     * description 获取某个月份的天数
     * param [date]
     * return int
     * author zzw
     * createTime 2021/5/26 16:56
     **/
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * description 获取日期对应的map
     * param [date]
     * return java.util.Map<java.time.LocalDate,java.lang.String>
     * author zzw
     * createTime 2021/5/26 17:28
     **/
    public static Map<LocalDate, String> getAllDays(Date date) {
        LinkedHashMap<LocalDate, String> map = new LinkedHashMap<>();
        int days = getDaysOfMonth(date);
        // date转换为localdatetime
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        int month = localDateTime.getMonthValue();
        if (days > 0) {
            for (int i = 1; i <= days; i++) {
                String myMonth = String.format("%02d", month);
                String myDay = String.format("%02d", i);
                map.put(localDateTime.withDayOfMonth(i).toLocalDate(), myMonth + myDay);
            }
        }
        return map;
    }

    public static Map<LocalDate, LocalDate> getAllDays(String date) throws ParseException {
        LinkedHashMap<LocalDate, LocalDate> map = new LinkedHashMap<>();
        Date tempDate = DateUtil.string2Date(date);
        LocalDate localDate = DateUtil.string2LocalDate(date);
        DateUtil.getLastDayOfMonth(localDate.getYear(), localDate.getMonthValue());
        int days = getDaysOfMonth(tempDate);
        // date转换为localdatetime
        Instant instant = tempDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        if (days > 0) {
            for (int i = localDate.getDayOfMonth(); i <= days; i++) {
                map.put(localDateTime.withDayOfMonth(i).toLocalDate(), localDateTime.withDayOfMonth(i).toLocalDate());
            }
        }
        return map;
    }

    /**
     * string 转 date
     *
     * @return
     */
    public static Date string2Date(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        return simpleDateFormat.parse(dateString);
    }


    /**
     * 获取某年某月的第一天
     *
     * @throws
     * @Title:getFisrtDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取某月的最后一天
     *
     * @throws
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static Map<String, String> getFirstAndLastDate(String date) {
        if (!StringUtils.hasText(date)) {
            return null;
        }
        LocalDate localDate = DateUtil.string2LocalDate(date);
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        String startDate = DateUtil.getFirstDayOfMonth(year, month);
        String endDate = DateUtil.getLastDayOfMonth(year, month);
        Map<String, String> map = new HashMap<>();
        map.put("start_date", startDate);
        map.put("end_date", endDate);
        return map;
    }

    public static Map<LocalDate, LocalDate> getEndDay(String beginDate, Long days) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(beginDate);
        String endDate = DateUtil.string2LocalDate(beginDate).plusDays(days - 1).toString();
        Date dEnd = sdf.parse(endDate);
        return findDates(dBegin, dEnd);
    }

    public static Map<LocalDate, LocalDate> findDates(Date dBegin, Date dEnd) {
        LinkedHashMap<LocalDate, LocalDate> dateMap = new LinkedHashMap<>();
        dateMap.put(DateUtil.date2LocalDate(dBegin), DateUtil.date2LocalDate(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            dateMap.put(DateUtil.date2LocalDate(calBegin.getTime()), DateUtil.date2LocalDate(calBegin.getTime()));
        }
        return dateMap;
    }

    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return instant.atZone(zoneId).toLocalDate();
    }

    public static LocalTime hour2LocalTime(String hour) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(hour,dateTimeFormatter);
    }

    public static boolean IsInTimeRange(LocalTime now, String beginHm, String endHm) {
        LocalTime start = hour2LocalTime(beginHm);
        LocalTime end = hour2LocalTime(endHm);

        if (now.isAfter(start) && now.isBefore(end)){
            return true;
        }
       return false;
    }


    public static void main(String[] args) throws ParseException {
//        System.out.println(getEndDay("2021-06-17", 40L).toString());

//        System.out.println("是否在时间范围内：" + IsInTimeRange(LocalTime.now(),"11:12","13:05"));
        for (int i = 0; i < 22; i++){
            System.out.println(IdWorker.getId());
        }
    }

}

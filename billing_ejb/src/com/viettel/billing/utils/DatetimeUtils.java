package com.viettel.billing.utils;

import java.util.Calendar;
import java.util.Date;

public class DatetimeUtils {
    public static int _MONTH = 0;
    public static int _YEAR = 1;
    
    public static int[] getMonthYear() {
        Date date = new Date(); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        int[] lret = new int[2];
        lret[_MONTH] = cal.get(Calendar.MONTH);
        lret[_YEAR] = cal.get(Calendar.YEAR);
        return lret;
    }

    public static long getLastDayOfMonth() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(getLastDayOfMonth());
    }
}

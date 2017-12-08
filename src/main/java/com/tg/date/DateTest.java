package com.tg.date;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.tg.date.DateUtils.getEndTimeOfMonth;
import static com.tg.date.DateUtils.getStartTimeOfMonth;

/**
 * Created by linzc on 2017/6/19.
 */
public class DateTest {
    @Test
    public void getStartTimeOfMonthTest() {
        Date date = getStartTimeOfMonth(new Date());

        Calendar day = Calendar.getInstance();
        day.setTime(date);
        System.out.println(date);
        Assert.assertTrue(day.get(Calendar.DAY_OF_MONTH) == 1);
    }

    @Test
    public void getEndTimeOfMonthTest() {
        Date date = getEndTimeOfMonth(new Date());

        Calendar day = Calendar.getInstance();
        day.setTime(date);
        System.out.println(date);
        Assert.assertTrue(day.get(Calendar.DAY_OF_MONTH) == 30);
    }
}

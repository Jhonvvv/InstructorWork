package com.clm;

import com.clm.common.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

/**
 * @Author su
 * @Date 2021/12/9 16:44
 */
public class TestCalendar {

    @Test
    public void testMethod1(){
        Calendar calendar=Calendar.getInstance();//获取当前的日期对象
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(2019, 8, 20, 13, 37, 99);

        System.out.println(calendar);
        System.out.println(calendar.getTime());
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.MINUTE));

    }

    @Test
    public void testMethod2(){
        System.out.println(DateUtils.customNowTime("17:07"));

    }

    @Test
    public void testMethod3(){
        Calendar date = Calendar.getInstance();
        System.out.println(date.get(Calendar.YEAR));

    }
}

package com.todolist.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ParseDate {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date stringToDate(String str){
        Date date = new Date();
        // 날짜를 입력하지 않은 경우.
        if(str.equals(""))
            return date;

        try{
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = simpleDateFormat.parse(str);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return date;
    }
}

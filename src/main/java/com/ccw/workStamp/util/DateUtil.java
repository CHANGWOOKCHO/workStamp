package com.ccw.workStamp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil{
    
    /**
	 * 날짜("YYYYMMDD")형식의 문자열을 입력받은 구분기호가 적용된 형태로 반환한다.
	 *
	 * @author 조창욱
	 * @version 1.0
	 * @param 날짜("YYYYMMDD")와 구분기호
	 * @return 날짜("YYYY-MM-DD")
	 *
	 * */
    public static String DateFormat(String date){
        
       String yyyy = date.substring(0,4);
       String mm = date.substring(4,6);
       String dd = date.substring(6);
        
       String[] dateFormat = {yyyy, mm, dd}; 
        
        return String.join("-", dateFormat);
    }
    
    public static String DateFormat(String date,String delimiter){
        
       String yyyy = date.substring(0,4);
       String mm = date.substring(4,6);
       String dd = date.substring(6);
        
       String[] dateFormat = {yyyy, mm, dd}; 
        
        return String.join(delimiter, dateFormat);
    }
    
	/**
	 * 시간("HHMISS")형식의 문자열을 입력받은 구분기호가 적용된 형태로 반환한다. 
	 *
	 * @author 조창욱
	 * @version 1.0
	 * @param 시간("HHMISS")와 구분기호
	 * @return 시간("HH:MI:SS")
	 *
	 * */
    public static String TimeFormat(String time){
        
       String vsTime = time.length() == 4 ? time+"00" : time; 
        
       String hh = vsTime.substring(0,2);
       String mm = vsTime.substring(2,4);
       String ss = vsTime.substring(4);
        
       String[] dateFormat = {hh, mm, ss}; 
        
        return String.join(":", dateFormat);
    }
    
     public static String TimeFormat(String time,String delimiter){
        
       String vsTime = time.length() == 4 ? time+"00" : time; 
        
       String hh = vsTime.substring(0,2);
       String mm = vsTime.substring(2,4);
       String ss = vsTime.substring(4);
        
       String[] dateFormat = {hh, mm, ss}; 
        
        return String.join(delimiter, dateFormat);
    }
    
 	/**
 	 * 입력받은 두 날짜 간의 날짜차이값을 반환한다.
 	 *
 	 * @author 조창욱
 	 * @version 1.0
	 * @param 비교할 두 날짜
	 * @return 날짜차이값
 	 *
 	 * */ 
    public static long convert(String to, String from) throws Exception{
        
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        
        Date firstDate = yyyyMMdd.parse(to);
        Date secondDate = yyyyMMdd.parse(from);
        
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
       
        return diff;
    }
    
    public static long convert(Date to, Date from) throws Exception{
        
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        
        Date firstDate = yyyyMMdd.parse(yyyyMMdd.format(to));
        Date secondDate = yyyyMMdd.parse(yyyyMMdd.format(from));
        
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    
        return diff;
    }
    
    
    
    
    
    

}
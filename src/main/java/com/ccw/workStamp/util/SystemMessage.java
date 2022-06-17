package com.ccw.workStamp.util;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class SystemMessage {
	
	  public static JSONParser parser = new JSONParser();
	  public static String path = SystemMessage.class.getResource("").getPath();

	
	/**
     * BusinessExceptionのエラーメッセージをユーザーの言語に合わせて変換する
     * 
     * SQL 조회결과가 담긴 Map의 key값을 camel표기법의 형식으로 재정의한다. 
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * 
     **/
	public static String errMassage(String errCode, String lagauge){
	    try
	    {
	      Object obj;
	   
	      if (lagauge.equals("jp"))
	        obj = parser.parse(new FileReader(path + "system_jp.txt"));
	      else {
	        obj = parser.parse(new FileReader(path + "system_kr.txt"));
	      }

	      JSONObject jsonObject = (JSONObject)obj;

	      return (String)jsonObject.get(errCode);
	    }
	    catch (Exception err) {
	    }
	    return "fail get SystemMassge";
	}
}

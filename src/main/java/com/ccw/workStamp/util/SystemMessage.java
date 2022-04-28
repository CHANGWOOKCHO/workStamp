package com.ccw.workStamp.util;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SystemMessage {
	
	  public static JSONParser parser = new JSONParser();
	  public static String path = SystemMessage.class.getResource("").getPath();

	  public static String errMassage(String errCode, String lagauge)
	  {
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

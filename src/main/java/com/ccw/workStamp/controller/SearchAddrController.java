package com.ccw.workStamp.controller;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.rmi.server.ExportException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ccw.workStamp.service.SearchAddrService;
import com.ccw.workStamp.util.BusinessException;

@Controller
@CrossOrigin("*")
@SuppressWarnings("unchecked")
public class SearchAddrController {
	
    @Autowired
    SearchAddrService searchAddrService;
    
    /**
     * 入力されたキーワードを住所検索APIに送信して住所情報を検索する
     *  
     * 입력받은 키워드를 행정안전부API로 전송하여 주소정보를 조회한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param キーワード
     *　　　　　검색어(keyword)
     * @return 우편번호, 도로명주소
     *　　　　　郵便番号、町名
     * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     *
     *            서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    @RequestMapping(value="/retrieveAddrCmd", consumes="application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> retrieveAddrCmd(@RequestBody HashMap<String, Object> requestMap) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commData = new HashMap<String, Object>();
        List<Map<String,Object>> loopData = new ArrayList<Map<String,Object>>();
        
        try{
            
            Map<String, Object> tempMap = searchAddrService.retrieveAddr((Map<String, Object>) requestMap.get("commData"));
            commData = (Map<String, Object>) tempMap.get("commData");
            loopData = (List<Map<String, Object>>) tempMap.get("loopData"); 
            
            resultMap.put("rsltCd", 0);
            resultMap.put("errMsg", ""); 
            
        }catch(ExportException err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", err.getMessage());
            err.printStackTrace();  
        }catch(Exception err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", "주소검색 중 알 수 없는 오류가 발생하였습니다.");
             err.printStackTrace();  
        }
        
        resultMap.put("commData", commData);
        resultMap.put("loopData", loopData);
        

        return resultMap;
    }
    
    /**
     * 選択された住所のGPS座標をKAKAO APIを通じて検索する
     * 
     * 선택된 주소지에 대한 GPS좌표값을 카카오주소API를 통해 조회한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param 住所地名
     * 　　　　주소지명
     * @return 緯度、経度
     * 　　　　 위도 및 경도
     * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     * 
     *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    @RequestMapping(value="/retrieveGeolocationCmd", consumes="application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> retrieveGeolocationCmd(@RequestBody HashMap<String, Object> requestMap) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commData = new HashMap<String, Object>();
        List<Map<String,Object>> loopData = new ArrayList<Map<String,Object>>();
    
        try{
        
            commData = searchAddrService.retrieveGeolocation((Map<String, Object>) requestMap.get("commData"));
                
            resultMap.put("rsltCd", 0);
            resultMap.put("errMsg", ""); 
            
        }catch(ExportException err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", err.getMessage());
        }catch(Exception err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", "주소검색 중 알 수 없는 오류가 발생하였습니다.");
            err.printStackTrace();
        }
        
        resultMap.put("commData", commData);
        resultMap.put("loopData", loopData);
        
        return resultMap;    
    }
    
    /**
     * デバイスを通じて認識されたGPS情報をKAKAO APIに送信して住所を検索する。
     * 
     * 디바이스를 통해 인식된 GPS정보를 카카오API로 전송하여 주소지를 조회한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param  緯度、経度
     * 　　　　 위도 및 경도
     * @return 郵便番号、基本住所
     * 　　　　 우편번호, 기본주소
     * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     * 
     *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    @RequestMapping(value="/retrieveGpsCmd", consumes="application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> retrieveGpsCmd(@RequestBody HashMap<String, Object> requestMap) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commData = new HashMap<String, Object>();
        List<Map<String,Object>> loopData = new ArrayList<Map<String,Object>>();
    
        try{
            commData = searchAddrService.retrieveGps((Map<String, Object>) requestMap.get("commData"));
                
            resultMap.put("rsltCd", 0);
            resultMap.put("errMsg", ""); 
            
        }catch(ExportException err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", err.getMessage());
        }catch(Exception err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", "주소검색 중 알 수 없는 오류가 발생하였습니다.");
            err.printStackTrace();
        }
        
        resultMap.put("commData", commData);
        resultMap.put("loopData", loopData);
        
        return resultMap;    
    }

    
    
    
    
}

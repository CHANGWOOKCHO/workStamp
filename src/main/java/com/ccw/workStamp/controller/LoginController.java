package com.ccw.workStamp.controller;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.rmi.server.ExportException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ccw.workStamp.service.LoginService;
import com.ccw.workStamp.util.BusinessException;


@Controller
@CrossOrigin("*")
@SuppressWarnings("unchecked")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
    @Autowired
    LoginService loginService;
    
    /**
     * sns간편로그인 고유ID 값을 바탕으로 해당 사용자가 회원가입되어 있는지를 조회한다.
     * (조회결과가 있다면 APP에서는 메인페이지로 이동, 없다면 회원가입페이지로 이동)
     * 
     * @author 조창욱
     * @version 1.0
     * @param sns간편로그인 타입 및 고유ID
     * @return 조회결과가 있다면 사용자의 주요정보가 들어있는 Map, 없다면 비어있는 Map을 반환
     * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    @RequestMapping(value="/retrieveUserInfoCmd", consumes="application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> retrieveUserInfoCmd(@RequestBody HashMap<String, Object> requestMap) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commData = new HashMap<String, Object>();
        List<Map<String,Object>> loopData = new ArrayList<Map<String,Object>>();
       
        try{
             
            resultMap.put("rsltCd", 0);
            resultMap.put("errMsg", "");
            
            commData = loginService.retrieveUserInfo((Map<String, Object>) requestMap.get("commData"));
            
        }catch(BusinessException err){      
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", err.toString());
        }catch(Exception err){
            resultMap.put("rsltCd", -397);
            resultMap.put("errMsg", "회원정보 조회 중 오류가 발생하였습니다.");
            err.printStackTrace();  
        }
        
        resultMap.put("commData", commData);
        resultMap.put("loopData", loopData);
        
       return resultMap;
    }
    
	
}

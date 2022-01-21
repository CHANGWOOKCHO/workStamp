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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccw.workStamp.service.SignUpService;

@Controller
@CrossOrigin("*")
@SuppressWarnings("unchecked")
public class SignUpController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
	
    @Autowired
    SignUpService signUpService;
    
    /**
     * 입력받은 회원정보를 바탕으로 workStamp APP의 사용자를 등록한다.
     * 
     * @author 조창욱
     * @version 1.0
     * @param SNS종류, SNS로그인일련번호, 사용자명, 회사정보
     * @return SNS종류, SNS로그인일련번호, 사용자명, 회사정보, 사용자일련번호
     * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    @RequestMapping(value="/registUserCmd", consumes="application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> registUserCmd(@RequestBody HashMap<String, Object> requestMap) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commData = new HashMap<String, Object>();
        List<Map<String,Object>> loopData = new ArrayList<Map<String,Object>>();

        try{
            commData = signUpService.registUser((Map<String, Object>) requestMap.get("commData"));
            
            resultMap.put("rsltCd", 0);
            resultMap.put("errMsg", "");
        }catch(ExportException err){
             resultMap.put("rsltCd", -1);
             resultMap.put("errMsg", err.getMessage());
        }catch(Exception err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", "회원가입 중 알 수 없는 오류가 발생하였습니다.");
            err.printStackTrace();
        }
        
        resultMap.put("commData", commData);
        resultMap.put("loopData", loopData);
        
        return resultMap;
    }
    
    /**
     * 입력된 조건에 맞는 등록된 회사정보를 조회한다. 현재위치(searchType == 3)로 검색 시에는 
	   반경 200m 이내에 등록된 회사들을 찾으며, 개인정보 보호를 위해 관리자 이름의 가운데는 '*'로 블라인드 처리한다.
     * 
     * @author 조창욱
     * @version 1.0
     * @param 검색조건, 키워드, 현재 위도 및 경도
     * @return 회사일련번호,회사명, 관리자명, 부서명, 회사주소정보
     * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    @RequestMapping(value="/retrieveCompanyInfoCmd", consumes="application/json", produces = "application/json;charset=UTF-8")
    public @ResponseBody Map<String, Object> retrieveCompanyInfoCmd(@RequestBody HashMap<String, Object> requestMap) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> commData = new HashMap<String, Object>();
        List<Map<String,Object>> loopData = new ArrayList<Map<String,Object>>();
        
        try{

            loopData = signUpService.retrieveCompanyInfo((Map<String, Object>) requestMap.get("commData"));
            
            resultMap.put("rsltCd", 0);
            resultMap.put("errMsg", "");
         
        }catch(ExportException err){
             resultMap.put("rsltCd", -1);
             resultMap.put("errMsg", err.getMessage());   
        }catch(Exception err){
            resultMap.put("rsltCd", -1);
            resultMap.put("errMsg", "근무지 조회 중 알 수 없는 오류가 발생하였습니다.");
            err.printStackTrace();
        }
        
        resultMap.put("commData", commData);
        resultMap.put("loopData", loopData);
        
        return resultMap;
    }
    
    
    
}

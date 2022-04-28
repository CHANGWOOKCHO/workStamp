package com.ccw.workStamp.controller;

import com.ccw.workStamp.service.WorkTimeService;
import com.ccw.workStamp.util.BusinessException;
import com.ccw.workStamp.util.SystemMessage;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin({"*"})
public class WorkTimeController
{

  @Autowired
  WorkTimeService workTimeService;

  
  /**
   * 조회일자의 츨퇴근입력 정보 및 출퇴근승인정보를 출력한다.
   * 
   * @author 조창욱
   * @version 1.0
   * @param 사용자일련번호, 조회일자
   * @return 출근시간, 퇴근시간, 출근승인상태, 퇴근승인상태, 응답코드, 오류메시지
   * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/ 
  @RequestMapping(value={"/retreiveWorkInfoCmd"}, consumes={"application/json"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Map<String, Object> retreiveWorkInfoCmd(@RequestBody HashMap<String, Object> requestMap)
  {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    Map<String, Object> commData = new HashMap<String, Object>();
    List<Map<String, Object>> loopData = new ArrayList<Map<String, Object>>();

    String language = ((Map)requestMap.get("commData")).get("language").toString();
    
    try
    {
      loopData = this.workTimeService.retreiveWorkInfo((Map)requestMap.get("commData"));

      resultMap.put("rsltCd", Integer.valueOf(0));
      resultMap.put("errMsg", "");
    } catch (BusinessException err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage(err.getMessage(), language));
    } catch (Exception err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage("-397", language));
      err.printStackTrace();
    }

    resultMap.put("commData", commData);
    resultMap.put("loopData", loopData);
    return resultMap;
  }
  
  /**
   * 주간 츨퇴근정보를 출력한다.
   * 
   * @author 조창욱
   * @version 1.0
   * @param 사용자일련번호, 조회시작일자, 조회종료일자
   * @return 근무일, 출근시간, 퇴근시간, 응답코드, 오류메시지
   * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/ 
  @RequestMapping(value={"/retreiveWeekWorkInfoCmd"}, consumes={"application/json"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Map<String, Object> retreiveWeekWorkInfoCmd(@RequestBody HashMap<String, Object> requestMap) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    Map<String, Object> commData = new HashMap<String, Object>();
    List<Map<String, Object>> loopData = new ArrayList<Map<String, Object>>();

    String language = ((Map)requestMap.get("commData")).get("language").toString();
    try
    {
      System.out.println("requestMap : " + requestMap.toString());
      loopData = this.workTimeService.retreiveWeekWorkInfo((Map)requestMap.get("commData"));

      resultMap.put("rsltCd", Integer.valueOf(0));
      resultMap.put("errMsg", "");
    } catch (BusinessException err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage(err.getMessage(), language));
    } catch (Exception err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage("-397", language));
      err.printStackTrace();
    }

    resultMap.put("commData", commData);
    resultMap.put("loopData", loopData);
    return resultMap;
  }
  
  /**
   * 현재시간 출퇴근시간을 등록하거나 사용자가 입력한 시간으로 춭퇴근승인요청을 한다.
   * 
   * @author 조창욱
   * @version 1.0
   * @param 사용자일련번호, 근무일자
   * @return 응답코드, 오류메시지
   * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
  @RequestMapping(value={"/registWorkInfoCmd"}, consumes={"application/json"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Map<String, Object> registWorkInfoCmd(@RequestBody HashMap<String, Object> requestMap) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    Map<String, Object> commData = new HashMap<String, Object>();
    List<Map<String, Object>> loopData = new ArrayList<Map<String, Object>>();

    String language = ((Map)requestMap.get("commData")).get("language").toString();
    try
    {
      System.out.println("requestMap : " + requestMap.toString());
      this.workTimeService.registWorkInfo((Map)requestMap.get("commData"));

      resultMap.put("rsltCd", Integer.valueOf(0));
      resultMap.put("errMsg", "");
    } catch (BusinessException err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage(err.getMessage(), language));
    } catch (Exception err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage("-397", language));
      err.printStackTrace();
    }

    resultMap.put("commData", commData);
    resultMap.put("loopData", loopData);
    return resultMap;
  }
  
  
  /**
   * 사용자 - 일주일 간의 사용자의 출퇴근승인요청정보를 조회한다.
   * 관리자 - 소속 사용자들의 일주일간의 출퇴근승인요청정보를 조회한다.
   * 
   * @author 조창욱
   * @version 1.0
   * @param 사용자일련번호, 조회시작일, 조회종료일
   * @return 사용자명, 근무일, 출퇴근구분, 출퇴근시간, 승인사유, 승인상태 
   * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
  @RequestMapping(value={"/retreiveApprListCmd"}, consumes={"application/json"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Map<String, Object> retreiveApprListCmd(@RequestBody HashMap<String, Object> requestMap) {
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	Map<String, Object> commData = new HashMap<String, Object>();
	List<Map<String, Object>> loopData = new ArrayList<Map<String, Object>>();

    String language = ((Map)requestMap.get("commData")).get("language").toString();
    try
    {
      System.out.println("requestMap : " + requestMap.toString());
      loopData = this.workTimeService.retreiveApprList((Map)requestMap.get("commData"));

      resultMap.put("rsltCd", Integer.valueOf(0));
      resultMap.put("errMsg", "");
    } catch (BusinessException err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage(err.getMessage(), language));
    } catch (Exception err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage("-397", language));
      err.printStackTrace();
    }

    resultMap.put("commData", commData);
    resultMap.put("loopData", loopData);
    return resultMap;
  }
  
  /**
   * 사용자들의 툴퇴근승인요청에 대해 승인 혹은 반려처리 한다.(관리자기능)
   * 
   * @author 조창욱
   * @version 1.0
   * @param 사용자일련번호, 출퇴근구분, 출퇴근일시, 승인상태
   * @return 응답코드, 오류메시지
   * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
  @RequestMapping(value={"/registApprInfoCmd"}, consumes={"application/json"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Map<String, Object> registApprInfoCmd(@RequestBody HashMap<String, Object> requestMap) {
	  
	Map<String, Object> resultMap = new HashMap<String, Object>();
	Map<String, Object> commData = new HashMap<String, Object>();
	List<Map<String, Object>> loopData = new ArrayList<Map<String, Object>>();

    String language = ((Map)requestMap.get("commData")).get("language").toString();
    try
    {
      List loopList = (List)requestMap.get("loopData");

      this.workTimeService.registApprInfo(loopList);
      resultMap.put("rsltCd", Integer.valueOf(0));
      resultMap.put("errMsg", "");
    } catch (BusinessException err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage(err.getMessage(), language));
    } catch (Exception err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage("-397", language));
      err.printStackTrace();
    }

    resultMap.put("commonData", commData);
    resultMap.put("loopData", loopData);
    return resultMap;
  }
  
  /**
   * 사용자들의 일주간 근무정보를 출력한다.(관리자기능)
   * 
   * @author 조창욱
   * @version 1.0
   * @param 사용자일련번호, 조회시작일, 조회종료일
   * @return 사용자명, 일~토 출근시간, 일~토 퇴근시간,응답코드, 오류메시지
   * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
  @RequestMapping(value={"/retrieveEmpWorkInfo"}, consumes={"application/json"}, produces={"application/json;charset=UTF-8"})
  @ResponseBody
  public Map<String, Object> retrieveEmpWorkInfo(@RequestBody HashMap<String, Object> requestMap) {
	  
	Map<String, Object> resultMap = new HashMap<String, Object>();
	Map<String, Object> commData = new HashMap<String, Object>();
	List<Map<String, Object>> loopData = new ArrayList<Map<String, Object>>();

    String language = ((Map)requestMap.get("commData")).get("language").toString();
    try
    {
      loopData = this.workTimeService.retrieveEmpWorkInfo((Map)requestMap.get("commData"));
      resultMap.put("rsltCd", Integer.valueOf(0));
      resultMap.put("errMsg", "");
    } catch (BusinessException err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage(err.getMessage(), language));
    } catch (Exception err) {
      resultMap.put("rsltCd", Integer.valueOf(-1));
      resultMap.put("errMsg", SystemMessage.errMassage("-397", language));
      err.printStackTrace();
    }

    resultMap.put("commonData", commData);
    resultMap.put("loopData", loopData);
    return resultMap;
  }
}
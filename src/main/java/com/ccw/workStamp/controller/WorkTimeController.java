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
   * 検索日の通勤情報及び通勤承認情報を検討する。
   * 
   * 조회일자의 츨퇴근입력 정보 및 출퇴근승인정보를 출력한다.
　 * 
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param ユーザーシーケンス、検索日
   * 　　　　사용자일련번호, 조회일자
   * @return 出勤時間、退勤時間、出勤承認情報、退勤承認情報
   * 　　　　　출근시간, 퇴근시간, 출근승인상태, 퇴근승인상태
   * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
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
   * 週間通勤情報を検討する
   * 
   * 주간 츨퇴근정보를 조회한다.
   * 
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param ユーザーシーケンス、検索開始日、検索終了日
   *        사용자일련번호, 조회시작일자, 조회종료일자
   * @return 勤務日、出勤時間、退勤時間
   *　　　　　근무일, 출근시간, 퇴근시간
   * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
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
   * 現在の時間を通勤情報として登録すらか、ユーザーが入力した時間を通勤承認要請する
   *   
   * 현재시간 출퇴근시간을 등록하거나 사용자가 입력한 시간으로 춭퇴근승인요청을 한다.
   * 
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param ユーザーシーケンス、勤務日
   *　　　　　사용자일련번호, 근무일자
   * @return N／A
   * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
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
   * ユーザー：ユーザーの一週間の通勤承認要請情報を検索する 
   * 管理者：　所属しているユーザーたちの一週間の通勤承認要請情報を検索する
   *
   * 사용자 - 사용자의 일주일 간의 출퇴근승인요청정보를 조회한다.
   * 관리자 - 소속 사용자들의 일주일간의 출퇴근승인요청정보를 조회한다.
   *
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param ユーザーシーケンス、出勤時間、退勤時間
   *　　　　　사용자일련번호, 조회시작일, 조회종료일
   * @return ユーザー名、勤務日、出・退勤分類、出・退勤時間、要請理由、承認状態
   * 　　　　　사용자명, 근무일, 출퇴근구분, 출퇴근시간, 요청사유, 승인상태
   * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
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
   * ユーザーたちの出・退勤承認要請に対して承認又は拒否する(管理者機能)
   *
   * 사용자들의 출퇴근승인요청에 대해 승인 혹은 반려처리 한다.(관리자기능)
   * 
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param 사용자일련번호, 출퇴근구분, 출퇴근일시, 승인상태
   *　　　　　ユーザーシーケンス、出・退勤分類、出・退勤日時、承認状態
   * @return N／A
   * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
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
   *　ユーザーたちの一週間の勤務情報を検索する(管理者機能)
   *
   * 사용자들의 일주간 근무정보를 출력한다.(관리자기능)
   * 
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param ユーザーシーケンス、検索開始日、検索終了日
   *　　　　사용자일련번호, 조회시작일, 조회종료일
   * @return ユーザー名、日~土出勤時間、日~土退勤時間
   *　　　　　사용자명, 일~토 출근시간, 일~토 퇴근시간
   * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
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
package com.ccw.workStamp.service.impl;

import com.ccw.workStamp.service.WorkTimeService;
import com.ccw.workStamp.util.BusinessException;
import com.ccw.workStamp.util.DateUtil;
import java.io.PrintStream;
import java.rmi.server.ExportException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={Exception.class, ExportException.class})
public class WorkTimeServiceImpl implements WorkTimeService {

  @Autowired
  private SqlSession sqlSession;
  private static final String workTimeMapper = "com.ccw.workStamp.WorkTimeMapper";

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
  public List<Map<String, Object>> retreiveWorkInfo(Map<String, Object> commonMap) throws Exception {
	  
    Map<String, Object> workMap = this.sqlSession.selectMap("com.ccw.workStamp.WorkTimeMapper.retrieveWorkInfo", commonMap, "workYmd");
    Map<String, Object> apprMap = this.sqlSession.selectMap("com.ccw.workStamp.WorkTimeMapper.retrieveApprInfo", commonMap, "workYmd");

    for (String workYmd : apprMap.keySet()){
    	
      Map<String, Object> tempApprMap = (Map)apprMap.get(workYmd);

      if (workMap.containsKey(workYmd)) {
        Map<String, Object> tempWorkMap = (Map)workMap.get(workYmd);

        String workStartApprCd = tempApprMap.getOrDefault("workStartApprCd", "").toString();
        String workEndApprCd = tempApprMap.getOrDefault("workEndApprCd", "").toString();

        if ((workStartApprCd.equals("R")) || (workStartApprCd.equals("T"))) {
          tempWorkMap.put("workStartYmd", tempApprMap.getOrDefault("workStartYmd", ""));
          tempWorkMap.put("workStartTime", tempApprMap.getOrDefault("workStartTime", ""));
          tempWorkMap.put("workStartApprCd", tempApprMap.getOrDefault("workStartApprCd", ""));
        }

        if ((workEndApprCd.equals("R")) || (workEndApprCd.equals("T"))) {
          tempWorkMap.put("workEndYmd", tempApprMap.getOrDefault("workEndYmd", ""));
          tempWorkMap.put("workEndTime", tempApprMap.getOrDefault("workEndTime", ""));
          tempWorkMap.put("workEndApprCd", tempApprMap.getOrDefault("workEndApprCd", ""));
        }

      }
      else if (workYmd != null) {
        workMap.put(workYmd, tempApprMap);
      }

    }

    Object[] mapKey = workMap.keySet().toArray();
    Arrays.sort(mapKey);

    List<Map<String, Object>> rsltList = new ArrayList(workMap.values());

    return rsltList;
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
  public List<Map<String, Object>> retreiveWeekWorkInfo(Map<String, Object> commonMap) throws Exception{
	  
	List<Map<String, Object>> rsltList = new LinkedList();
    Map<String, Object> workMap = this.sqlSession.selectMap("com.ccw.workStamp.WorkTimeMapper.retrieveWeekWorkInfo", commonMap, "workYmd");

    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    Date workStartYmd = yyyyMMdd.parse(commonMap.get("workStartYmd").toString());

    for (int i = 0; i < 7; i++){
      workStartYmd.setDate(i == 0 ? workStartYmd.getDate() : workStartYmd.getDate() + 1);
      String key = yyyyMMdd.format(workStartYmd);

      rsltList.add(workMap.containsKey(key) ? (Map)workMap.get(key) : emptyMap(key));
    }
    return rsltList;
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
  public boolean registWorkInfo(Map<String, Object> commonMap) throws Exception {
    boolean isAuto = commonMap.get("autoYn").toString().equals("Y");

    if (isAuto)
      registWorkInfoByAuto(commonMap);
    else {
      registWorkInfoByManual(commonMap);
    }

    return true;
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
  public List<Map<String, Object>> retreiveApprList(Map<String, Object> commonMap) throws Exception {
    return this.sqlSession.selectList("com.ccw.workStamp.WorkTimeMapper.retreiveApprList", commonMap);
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
  public boolean registApprInfo(List<Map<String, Object>> commonList) throws Exception {
    String apprCd = null;
    for (Map<String, Object> commonMap : commonList)
    {
      apprCd = commonMap.get("apprCd").toString();

      if (apprCd.equals("A")) {
        this.sqlSession.insert("com.ccw.workStamp.WorkTimeMapper.registWorkInfo", commonMap);
        this.sqlSession.update("com.ccw.workStamp.WorkTimeMapper.updateApprInfo", commonMap);
      } else {
        this.sqlSession.update("com.ccw.workStamp.WorkTimeMapper.updateApprInfo", commonMap);
      }

    }

    return true;
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
  public List<Map<String, Object>> retrieveEmpWorkInfo(Map<String, Object> commonMap) throws Exception {
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    Date standDate = yyyyMMdd.parse(commonMap.get("startWorkYmd").toString());
    Map requestMap = new HashMap();

    for (int i = 0; i < 7; i++) {
      standDate.setDate(i == 0 ? standDate.getDate() : standDate.getDate() + 1);
      String value = yyyyMMdd.format(standDate);
      requestMap.put("day" + i, value);
    }

    requestMap.put("userSeq", commonMap.get("userSeq").toString());

    Map<String, Object> workMap = this.sqlSession.selectMap("com.ccw.workStamp.WorkTimeMapper.retrieveEmpWorkInfoList", requestMap, "userSeq");
    Map<String, Object> empMap = this.sqlSession.selectMap("com.ccw.workStamp.WorkTimeMapper.retrieveEmpInfoList", requestMap, "userSeq");

    for (Map.Entry entry : empMap.entrySet())
    {
      Map<String, Object> tempMap;
      
      if (workMap.containsKey(entry.getKey())) {
        tempMap = (Map)workMap.get(entry.getKey());
      } else {
        tempMap = new HashMap<String, Object>();
        tempMap.put("userSeq", ((Map)entry.getValue()).get("userSeq"));
        tempMap.put("userNm", ((Map)entry.getValue()).get("userNm"));

        tempMap.put("day0Start", "");
        tempMap.put("day0End", "");
        tempMap.put("day1Start", "");
        tempMap.put("day1End", "");
        tempMap.put("day2Start", "");
        tempMap.put("day2End", "");
        tempMap.put("day3Start", "");
        tempMap.put("day3End", "");
        tempMap.put("day4Start", "");
        tempMap.put("day4End", "");
        tempMap.put("day5Start", "");
        tempMap.put("day5End", "");
        tempMap.put("day6Start", "");
        tempMap.put("day6End", "");
        tempMap.put("total", Integer.valueOf(0));
      }

      entry.setValue(tempMap);
    }

    Object[] mapKey = empMap.keySet().toArray();
    Arrays.sort(mapKey);

    List<Map<String, Object>> rsltList = new ArrayList(empMap.values());

    return rsltList;
  }
  
  /**
   * ユーザーが入力した時間をもとに出・退勤登録の承認要請する 
   * 사용자가 입력한 시간을 기준으로 출퇴근등록 승인요청을 한다. 
   * @call registWorkInfo()
   */
  private boolean registWorkInfoByManual(Map<String, Object> commonMap) throws Exception {
    String stampType = commonMap.get("stampType").toString();
    String tempYmd = DateUtil.DateFormat(commonMap.get("registYmd").toString());
    String tempTime = DateUtil.TimeFormat(commonMap.get("registTime").toString());
    String workYmd = commonMap.get("workYmd").toString();

    SimpleDateFormat yyyyMMDddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    Date nowTime = new Date(System.currentTimeMillis());
    Date registTime = yyyyMMDddHHmmss.parse(tempYmd + " " + tempTime);

    String nowYmd = yyyyMMdd.format(nowTime);
    int timeCompareCnt = registTime.compareTo(nowTime);

    System.out.println("JVM_DATE : " + nowTime);
    System.out.println("timeCompareCnt : " + timeCompareCnt);

    //근무일자가 현재를 기준으로 일주일이 경과한 경우 승인요청이 불가
    if (DateUtil.convert(nowYmd, workYmd) < -6L)
      throw new BusinessException("-108");  //등록일이 현재로부터 일주일이 경과하여 등록할 수 없습니다.
    
    //현재일자를 초과한 경우 승인요청은 불가
    if (DateUtil.convert(nowYmd, workYmd) > 0L) 
      throw new BusinessException("-109");	//등록일이 현재시간을 초과하였습니다. 재입력해주시기 바랍니다.
    
    //출퇴근등록요청 시간이 현재시간보다 큰 경우 승인요청 불가
    if (timeCompareCnt > 0) {
      throw new BusinessException("-110");  //근무시간이 현재시간을 초과하였습니다. 재입력해주시기 바랍니다.
    }

    commonMap.put("workTime", yyyyMMDddHHmmss.format(registTime));

    this.sqlSession.insert("com.ccw.workStamp.WorkTimeMapper.registApprInfo", commonMap);

    return true;
  }
  
  /**
   * 出勤又は退勤に対する承認要請情報があれば消した後に現在の時間をもとに出・退勤登録する。
   * 출근 혹은 퇴근에 대한 승인요청정보가 있다면 삭제 후 현재시간을 기준으로 출퇴근등록한다. 
   * @call registWorkInfo()
   */
  private boolean registWorkInfoByAuto(Map<String, Object> commonMap) throws Exception {
    String nowYmd = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));

    if (!nowYmd.equals(commonMap.get("workYmd").toString())) {
      throw new BusinessException("-107");
    }

    this.sqlSession.delete("com.ccw.workStamp.WorkTimeMapper.deleteApprInfo", commonMap);

    this.sqlSession.insert("com.ccw.workStamp.WorkTimeMapper.registWorkInfo", commonMap);

    return true;
  }
  
  /**
   * 出・退勤情報がない日にはけKeyだけの空いているMapにする
   * 출퇴근정보가 없는 일자는 key값만 존재하는 빈 Map으로 채운다.
   */
  private Map<String, Object> emptyMap(String workYmd) throws Exception {
    Map rsltMap = new HashMap();

    rsltMap.put("workYmd", workYmd);
    rsltMap.put("workStartYmd", "");
    rsltMap.put("workStartTime", "");
    rsltMap.put("workEndYmd", "");
    rsltMap.put("workEndTime", "");
    rsltMap.put("minute", Integer.valueOf(0));

    return rsltMap;
  }
  
}
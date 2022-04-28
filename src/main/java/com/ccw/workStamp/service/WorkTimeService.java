package com.ccw.workStamp.service;

import java.util.List;
import java.util.Map;

public interface WorkTimeService {

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
	  public List<Map<String, Object>> retreiveWorkInfo(Map<String, Object> paramMap) throws Exception;
 	
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
	  public boolean registWorkInfo(Map<String, Object> paramMap) throws Exception;
	  
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
	  public boolean registApprInfo(List<Map<String, Object>> paramList) throws Exception;

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
	  public List<Map<String, Object>> retreiveWeekWorkInfo(Map<String, Object> paramMap) throws Exception;

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
	  public List<Map<String, Object>> retreiveApprList(Map<String, Object> paramMap) throws Exception;
	  
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
	  public List<Map<String, Object>> retrieveEmpWorkInfo(Map<String, Object> paramMap) throws Exception;
	
}

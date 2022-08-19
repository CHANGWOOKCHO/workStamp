package com.ccw.workStamp.service;

import java.util.List;
import java.util.Map;

public interface WorkTimeService {

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
   * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/ 
	public List<Map<String, Object>> retreiveWorkInfo(Map<String, Object> paramMap) throws Exception;
 	
  /**
   * 週間通勤情報を検索する
   * 
   * 주간 츨퇴근정보를 조회한다.
   * 
   * @author ジョチャンウク／조창욱
   * @version 1.0
   * @param ユーザーシーケンス、検索開始日、検索終了日
   *        사용자일련번호, 조회시작일자, 조회종료일자
   * @return 勤務日、出勤時間、退勤時間
   *　　　　　근무일, 출근시간, 퇴근시간
   * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/ 
	public boolean registWorkInfo(Map<String, Object> paramMap) throws Exception;
	  
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
   * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
	public boolean registApprInfo(List<Map<String, Object>> paramList) throws Exception;

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
   * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/  
	public List<Map<String, Object>> retreiveWeekWorkInfo(Map<String, Object> paramMap) throws Exception;

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
   * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
	public List<Map<String, Object>> retreiveApprList(Map<String, Object> paramMap) throws Exception;
	  
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
   * @exception　サーバーで定義したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
   *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
   * 
   *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
   *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
   * 
   **/
	public List<Map<String, Object>> retrieveEmpWorkInfo(Map<String, Object> paramMap) throws Exception;
	
}

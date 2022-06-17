package com.ccw.workStamp.service;

import java.util.Map;

import java.util.List;

public interface SignUpService {
    
    /**
     * 入力した会員情報をもとにworkStamp APPの会員登録する。
     *   
     * 입력한 회원정보를 바탕으로 workStamp APP의 사용자를 등록한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param ソーシャルメディアのタイプ、UID、ユーザー名、会社情報
     * 　　　　 SNS종류, SNS로그인일련번호, 사용자명, 회사정보
     * @return ソーシャルメディアのタイプ、UID、ユーザー名、会社情報、ユーザーシーケンス
     　　　　　　SNS종류, SNS로그인일련번호, 사용자명, 회사정보, 사용자일련번호
     * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     * 
     *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    public Map<String, Object> registUser(Map<String, Object> commonMap) throws Exception;
	
    /**
     * 入力された条件に合う会社情報を検索する。現在の位置(searchType == 3)で検索した場合は
     * 半径200m以内に登録された会社を探し、個人情報保護のために管理者の名前の一部は'*'でブラインド処理する。
     *  
     * 입력된 조건에 맞는 등록된 회사정보를 조회한다. 현재위치(searchType == 3)로 검색 시에는 
	 * 반경 200m 이내에 등록된 회사들을 찾으며, 개인정보 보호를 위해 관리자 이름의 가운데는 '*'로 블라인드 처리한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param 検索条件、キーワード、緯度及び経度
     * 　　　 검색조건, 키워드, 현재 위도 및 경도 　　　　
     * @return 会社シーケンス、会社名、管理者名、チーム名、会社住所情報
     *  　　　　회사일련번호,회사명, 관리자명, 부서명, 회사주소정보　　　
     * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     * 
     *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    public List<Map<String, Object>> retrieveCompanyInfo(Map<String, Object> searchInfoMap) throws Exception;
    
}

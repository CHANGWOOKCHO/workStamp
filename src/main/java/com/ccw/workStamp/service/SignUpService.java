package com.ccw.workStamp.service;

import java.util.Map;

import java.util.List;

public interface SignUpService {
    
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
    public Map<String, Object> registUser(Map<String, Object> commonMap) throws Exception;
	
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
    public List<Map<String, Object>> retrieveCompanyInfo(Map<String, Object> searchInfoMap) throws Exception;
    
}

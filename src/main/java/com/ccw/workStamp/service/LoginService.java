package com.ccw.workStamp.service;

import java.util.Map;

public interface LoginService {
    
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
    public Map<String, Object> retrieveUserInfo(Map<String, Object> commendMap) throws Exception;
	
}

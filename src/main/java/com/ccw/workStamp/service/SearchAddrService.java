package com.ccw.workStamp.service;

import java.util.Map;

public interface SearchAddrService {
    
    /**
     * 입력받은 키워드를 행정안전부API로 전송하여 주소정보를 조회한다.
     * 
     * @author 조창욱
     * @version 1.0
     * @param 검색어(keyword)
     * @return 우편번호, 도로명주소
     * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
	public Map<String, Object> retrieveAddr(Map<String, Object> commendMap) throws Exception;
    
    /**
     * 선택된 주소지에 대한 GPS좌표값을 카카오주소API를 통해 조회한다.
     * (행정안전부API만으로는 GPS좌표값에 대한 조회가 불가하며, retrieveAddrCmd에서 검색된 주소지 GPS좌표값을 다 조회하기에는 시간이 오래걸림으로
     *  사용자가 조회된 주소지리스트에서 선택한 단일주소지 정보에 대해서만 GPS좌표를 조회한다.)
     * 
     * @author 조창욱
     * @version 1.0
     * @param 주소지명
     * @return 위도 및 경도
     * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    public Map<String, Object> retrieveGeolocation(Map<String, Object> commendMap) throws Exception;
    
    /**
     * 디바이스를 통해 인식된 GPS정보를 카카오API로 전송하여 주소지를 조회한다.
     * (도로명주소지에서 일정거리를 벗어나 있는 경우 지번주소만 조회될 수 있다.)
     * 
     * @author 조창욱
     * @version 1.0
     * @param 위도 및 경도
     * @return 우편번호, 기본주소
     * @exception 서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    public Map<String, Object> retrieveGps(Map<String, Object> commendMap) throws Exception; 
    
}

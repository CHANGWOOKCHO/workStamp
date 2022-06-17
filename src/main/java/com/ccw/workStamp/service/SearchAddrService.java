package com.ccw.workStamp.service;

import java.util.Map;

public interface SearchAddrService {
    
    /**
     * 入力されたキーワードを住所検索APIに送信して住所情報を検索する
     *  
     * 입력받은 키워드를 행정안전부API로 전송하여 주소정보를 조회한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param キーワード
     *　　　　　검색어(keyword)
     * @return 우편번호, 도로명주소
     *　　　　　郵便番号、町名
     * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     *
     *            서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
	public Map<String, Object> retrieveAddr(Map<String, Object> commendMap) throws Exception;
    
    /**
     * 選択された住所のGPS座標をKAKAO APIを通じて検索する
     * 
     * 선택된 주소지에 대한 GPS좌표값을 카카오주소API를 통해 조회한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param 住所地名
     * 　　　　주소지명
     * @return 緯度、経度
     * 　　　　 위도 및 경도
     * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     * 
     *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    public Map<String, Object> retrieveGeolocation(Map<String, Object> commendMap) throws Exception;
    
    /**
     * デバイスを通じて認識されたGPS情報をKAKAO APIに送信して住所を検索する。
     * 
     * 디바이스를 통해 인식된 GPS정보를 카카오API로 전송하여 주소지를 조회한다.
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * @param  緯度、経度
     * 　　　　 위도 및 경도
     * @return 郵便番号、基本住所
     * 　　　　 우편번호, 기본주소
     * @exception　サーバーで定期したbussinessExceptionが起こる場合はrsltCdは-1、errMsgはbussinessExaptionのメッセージをリターン
     *          　思わなかったExceptionが起こる場合はrsltCdは-1、errMsgはじてい指定されたメッセージをリターン
     * 
     *             서버가 정의한 bussinessException이 일어나는 경우 rsltCd는 -1, errMsg는 bussinessExaption의 메시지를 반환
     *            의도치 않은 Exception이 일어나는 경우 rsltCd는 -1, errMsg는 지정된 메시지를 반환 
     * 
     **/
    public Map<String, Object> retrieveGps(Map<String, Object> commendMap) throws Exception; 
    
}

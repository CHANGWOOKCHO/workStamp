package com.ccw.workStamp.service.impl;

import com.ccw.workStamp.service.SearchAddrService;
import com.ccw.workStamp.util.BusinessException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Service
@SuppressWarnings("unchecked")
@Transactional(rollbackFor = {Exception.class, BusinessException.class})
public class SearchAddrServiceImpl implements SearchAddrService{
    
	@Value("${api.addr}")
    private String ADDR_KEY;
	@Value("${api.kakao}")
    private String KAKAO_KEY;
    private static final int TIMEOUT_VALUE = 5000;   // 5초
 
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
    public Map<String, Object> retrieveAddr(Map<String, Object> commendMap) throws Exception{
           
        Map<String , Object> resultMap = new HashMap<String, Object>();
        
        int currentPage = Integer.parseInt(commendMap.get("currentPage").toString()); //요청 변수 설정 (현재 페이지. currentPage : n > 0)
        int countPerPage = Integer.parseInt(commendMap.get("countPerPage").toString());  //요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100)
        String keyword = commendMap.get("keyword").toString();            //요청 변수 설정 (키워드)

        String apiUrl = "http://www.juso.go.kr/addrlink/addrLinkApi.do?"
                        +"currentPage="+currentPage
                        +"&countPerPage="+countPerPage
                        +"&keyword="+URLEncoder.encode(keyword,"UTF-8")
                        +"&confmKey="+ADDR_KEY
                        +"&resultType=json";
            
        URL url = new URL(apiUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        
        while(true){
            tempStr = br.readLine();
            if(tempStr == null) break;
            sb.append(tempStr);								// 응답결과 JSON 저장
        }
        br.close();
     
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(sb.toString());
        JSONObject jsonObj = (JSONObject) obj;

        Map<String, Object> jsonMap = (Map<String, Object>) jsonObj.get("results");
        
        Map<String, Object> commonMap = (Map<String, Object>) jsonMap.get("common");
        List<Map<String, Object>> jusoList = (List<Map<String, Object>>) jsonMap.get("juso");
        
        if((commonMap.get("errorCode").toString()).equals("0") == false){
             throw new BusinessException(commonMap.get("errorMessage").toString());
        }
        
        if(jusoList.size() == 0){
            throw new BusinessException("검색결과가 존재하지 않습니다.");
        }
        
        int totalCount = Integer.parseInt(commonMap.get("totalCount").toString());
        int totalPage = totalCount/countPerPage;
        
        if((totalCount%countPerPage) != 0){
            totalPage++;
        }
        
        commonMap.put("totalPage", totalPage);
        
        resultMap.put("commData", commonMap);
        resultMap.put("loopData", jusoList);
        
    
       return resultMap;
    }

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
    public Map<String, Object> retrieveGeolocation(Map<String, Object> commendMap) throws Exception{
        
        String keyword = commendMap.get("keyword").toString();            //요청 변수 설정 (키워드)
       
        HttpURLConnection urlConnection = null;
        InputStream dis = null;
        
        URL url = new URL("https://dapi.kakao.com/v2/local/search/address.json?query="+URLEncoder.encode(keyword,"UTF-8"));
        urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setConnectTimeout(TIMEOUT_VALUE);
        urlConnection.setReadTimeout(TIMEOUT_VALUE);
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("User-Agent", "Java-Client");	// https 호출시 user-agent 필요
        urlConnection.setRequestProperty("X-Requested-With", "curl");
        urlConnection.setRequestProperty("Authorization", KAKAO_KEY);
        urlConnection.setDoInput(true);
        
        if( urlConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
             dis= urlConnection.getInputStream();
        }else{
             throw new BusinessException("GPS 정보 조회 중 서버와의 연결에 실패하였습니다.");
        } 
       
        BufferedReader br = new BufferedReader(new InputStreamReader(dis,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String tempStr = null;

        while(true){
            tempStr = br.readLine();
            if(tempStr == null) break;
            sb.append(tempStr);								// 응답결과 JSON 저장
        }
        br.close();
        System.out.println("kakaoResponse : " + sb.toString());
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(sb.toString());
        JSONObject jsonObj = (JSONObject) obj;

        List<Map<String, Object>> resultList = (List<Map<String, Object>>) jsonObj.get("documents");
        Map<String, Object> tempMap = (Map<String, Object>) resultList.get(0);      
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        resultMap.put("x", tempMap.get("x"));
        resultMap.put("y", tempMap.get("y"));
       
        return resultMap;
    }
    
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
    public Map<String, Object> retrieveGps(Map<String, Object> commendMap) throws Exception{
        
        String latitude = commendMap.get("latitude").toString();
        String longitude = commendMap.get("longitude").toString();
       
        HttpURLConnection urlConnection = null;
        InputStream dis = null;
    
        System.out.println("commendMap : "+ commendMap);
        System.out.println("url : "+ "https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+longitude+"&y="+latitude+"&input_coord=WGS84");
            
        URL url = new URL("https://dapi.kakao.com/v2/local/geo/coord2address.json?x="+longitude+"&y="+latitude+"&input_coord=WGS84");
        urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setConnectTimeout(TIMEOUT_VALUE);
        urlConnection.setReadTimeout(TIMEOUT_VALUE);
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("User-Agent", "Java-Client");	// https 호출시 user-agent 필요
        urlConnection.setRequestProperty("X-Requested-With", "curl");
        urlConnection.setRequestProperty("Authorization", KAKAO_KEY);
        urlConnection.setDoInput(true);
        
        System.out.println("ADDR_KEY :"+ ADDR_KEY);
        System.out.println("KAKAO_KEY :"+ KAKAO_KEY);
        
        if( urlConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {
             dis= urlConnection.getInputStream();
        }else{
             //throw new BusinessException("GPS 정보 조회 중 서버와의 연결에 실패하였습니다.");
            dis = urlConnection.getErrorStream();
        } 
        
        BufferedReader br = new BufferedReader(new InputStreamReader(dis,"UTF-8"));
        StringBuffer sb = new StringBuffer();
        String tempStr = null;

        while(true){
            tempStr = br.readLine();
            if(tempStr == null) break;
            sb.append(tempStr);								// 응답결과 JSON 저장
        }
        br.close();
        System.out.println("kakaoResponse : " + sb.toString());
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(sb.toString());
        JSONObject jsonObj = (JSONObject) obj;

        List<Map<String, Object>> resultList = (List<Map<String, Object>>) jsonObj.get("documents");
        Map<String, Object> tempMap = (Map<String, Object>) resultList.get(0);      
        
        Map<String, Object> resultMap = new HashMap<String, Object>(); 
        Map<String, Object> roadMap = (Map<String, Object>) tempMap.get("road_address");
        Map<String, Object> jibunMap = (Map<String, Object>) tempMap.get("address");
        
        System.out.println("roadMap : "  + roadMap);
        System.out.println("jibunMap : "  + jibunMap);
        
        if(roadMap == null && jibunMap == null){ //주소정보가 없을 경우
             throw new BusinessException("현재 위치에 대한 주소정보가 존재하지 않습니다.");
        }else if(roadMap == null){ //도로명 주소를 못 받아오는 경우가 지번으로 대체 있음.
            
            resultMap.put("type", "jibun");
            resultMap.put("zipCode", jibunMap.get("zip_code").toString());
            resultMap.put("basicAddr", jibunMap.get("address_name").toString());
            
        }else{
            
            resultMap.put("type", "road");
            resultMap.put("zipCode", roadMap.get("zone_no").toString());
            resultMap.put("basicAddr", roadMap.get("address_name").toString());
        }
        
        return resultMap;
    };
    
    
    
} 
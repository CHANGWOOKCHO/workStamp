package com.ccw.workStamp.service.impl;

import com.ccw.workStamp.service.SignUpService;
import com.ccw.workStamp.util.BusinessException;

import java.nio.charset.Charset;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.session.SqlSession;

@Service
@Transactional(rollbackFor = {Exception.class, ExportException.class})
public class SignUpServiceImpl implements SignUpService{
    
     @Autowired
    private SqlSession sqlSession;
	
	private final static String signUpMapper = "com.ccw.workStamp.SignUpMapper";
       
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
    public Map<String, Object> registUser(Map<String, Object> commonMap) throws Exception{
        
    	//해당 사용자가 기등록되어 있는지 다시 한 번 확인한다.
        Integer registCnt = sqlSession.selectOne(signUpMapper+".retrieveRegistCnt", commonMap);
        
        if(registCnt != 0){
            throw new BusinessException("등록된 사용자 정보가 존재합니다.");
        }
        
        //관리자의 회원등록인 경우 함께 전달된 회사정보들도 COMPANYINFO 테이블에 등록한다.
        if(commonMap.get("adminYn").toString().equals("Y")){
        	
        	//useGeneratedKeys로 인해 comonMap의 회사일련번호(COMPSEQ)에 실제값이 등록된다.
            int companyInsertCnt = sqlSession.insert(signUpMapper+".insertCompanyInfo", commonMap);
        
            if(companyInsertCnt <= 0){
                throw new BusinessException("근무지 등록에 실패하였습니다.");
            }

        }
        
        //useGeneratedKeys로 인해 comonMap의 사용자일련번호(USERSEQ)에 실제값이 등록된다.
        int userInsertCnt = sqlSession.insert(signUpMapper+".insertUserInfo", commonMap);
        
        if(userInsertCnt <= 0){
            throw new BusinessException("사용자 등록에 실패하였습니다.");
        }
        
        return commonMap;
    }
    
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
    public List<Map<String, Object>> retrieveCompanyInfo(Map<String, Object> searchInfoMap) throws Exception{
       
    	//검색조건에 부합하는 회사정보가 존재하는지 조회한다.
        List<Map<String, Object>> resultList = sqlSession.selectList(signUpMapper+".retrieveCompanyInfo", searchInfoMap);
        
        if(resultList.size() == 0){
            throw new BusinessException("조회결과가 없습니다.");
        }
        
        return resultList;
    }
    
    
} 
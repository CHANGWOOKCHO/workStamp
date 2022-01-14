package com.ccw.workStamp.util;
/**
 * 잘못된 형태의 요청이 들어오는 경우 발생하는 예외
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = -357843356921318530L;
	
    /**
     * 서비스가 정의한 예외사항을 BusinessException라 정의하여 별도의 catch문에서 처리될 수 있도록 한다. 
     * 
     * @author 조창욱
     * @version 1.0
     * 
     **/
	
	public BusinessException(String msg) {
		super(msg);
	}
}
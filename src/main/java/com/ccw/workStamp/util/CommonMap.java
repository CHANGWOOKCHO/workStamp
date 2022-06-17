package com.ccw.workStamp.util;

import org.apache.commons.collections.map.ListOrderedMap;

public class CommonMap extends ListOrderedMap{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * SQLのSELECT結果のMapのkeyをcamel表記に変換する
     * 
     * SQL 조회결과가 담긴 Map의 key값을 camel표기법의 형식으로 재정의한다. 
     * 
     * @author ジョチャンウク／조창욱
     * @version 1.0
     * 
     **/
    public Object put(Object key, Object value){
        return super.put(camelCase((String) key), value);        
    }
    
    public String camelCase(String key){
        
        if(key.indexOf('_') < 0 && Character.isLowerCase(key.charAt(0))){
            return key;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        
        for(int i = 0; i < key.length(); i++){
            char currentChar = key.charAt(i);
            
            if(currentChar == '_'){
                nextUpper = true;
                continue;
            }
            
            if(nextUpper){
                result.append(Character.toUpperCase(currentChar));
                nextUpper = false;
            }else{
                result.append(Character.toLowerCase(currentChar));        
            }
        }
        
        
        return result.toString();
    }
    

}
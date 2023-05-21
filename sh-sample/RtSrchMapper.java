package com.shl.app.cus.rtf.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @desc    고객시스템 > RT 조회
 * <pre>
 * 
 * @author  bson
 * @since   2019.10.31
 * @version 1.0
 * @see
 * =================== 변경 내역 ==================
 * 날짜			변경자		내용
 * ------------------------------------------------
 * 2019.10.31.	bson		최초작성
 */
public interface RtSrchMapper {
	
	/**
	 * RT 조회
	 * @param HashMap<String, Object> param
	 * @return List<Map<String, Object>>
	 */
	
	public List<Map<String, Object>> selectRtSrchList(HashMap<String, Object> param);	
	
} 
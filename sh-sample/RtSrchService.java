package com.shl.app.cus.rtf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shl.common.exception.AppException;

/**
 * <pre>
 * @desc RT 조회 서비스 인터페이스
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
public interface RtSrchService {
	/**
	 * RT 조회
	 * @param HashMap<String, Object> param
	 * @return List<Map<String, Object>>
	 * @throws AppException
	 */

	public List<Map<String, Object>> selectRtSrchList(HashMap<String, Object> param);
	
}
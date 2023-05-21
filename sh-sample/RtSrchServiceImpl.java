package com.shl.app.cus.rtf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.shl.common.exception.AppException;
import com.shl.app.utils.func.SessionUtil;

import com.shl.app.cus.rtf.mapper.RtSrchMapper;
import com.shl.app.cus.rtf.service.RtSrchService;


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


@Service
public class RtSrchServiceImpl implements RtSrchService {
	@Resource(name="messageSource")
	MessageSource messageSource;
	
	@Autowired
    SqlSessionTemplate sqlSession;
	
	@Autowired
	SessionUtil sessionUtil;
	
	/**
	 * RT 조회
	 * @param HashMap<String, Object> param
	 * @return List<Map<String, Object>>
	 * @throws AppException
	 */
	
	@Override
	public List<Map<String, Object>> selectRtSrchList(HashMap<String, Object> param) {
		
		RtSrchMapper mapper = sqlSession.getMapper(RtSrchMapper.class);
		return mapper.selectRtSrchList(param);
		
	}
}
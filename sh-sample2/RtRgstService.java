package com.shl.app.cus.rtf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shl.app.cus.rtf.model.RtRgstVO;
import com.shl.app.utils.model.ComboCodeVO;
import com.shl.common.exception.AppException;

/**
 * <pre>
 * RT송장등록 서비스
 * <pre>
 * 
 * @author	eungsik
 * @since	2019.04.18
 * @version	1.0
 * @see
 * ================== 변경 내역 ==================
 * 날짜			변경자		내용
 * -----------------------------------------------
 * 2019.04.18	eungsik		최초작성
 */
public interface RtRgstService {
	
	/**
   	 * 같은 대표고객코드를 가진 브랜드번호 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	브랜드번호 목록
   	 */
	public List<ComboCodeVO> selectBrndList(String BRND_NUM);
	
	/**
   	 * AS가능 매장 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	매장 목록
   	 */
	public List<ComboCodeVO> selectAsOrgList(String BRND_NUM);
	
	/**
   	 * 같은 대표고객코드를 가진 브랜드번호 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	대표고객
   	 */
	public String selectRepzntCstr(String BRND_NUM);
	
    /**
   	 * RT등록자료 목록을 조회한다.
   	 * 
   	 * @param PU_BRND_NUM 고객번호
   	 * @param PU_BRND_ORG_NUM 발신지점
   	 * @param OPRT_DT 등록일자
   	 * @param RT_NUM
   	 * @return	RT등록자료 목록
   	 */
	public List<RtRgstVO> selectRtList(String PU_BRND_NUM, String PU_BRND_ORG_NUM, String OPRT_DT, String RT_NUM);
	
	
	 /**
   	 * RT등록자료를 저장 한다.
   	 *
   	 * @param dsRtList RT등록자료 데이터셋
   	 * @throws AppException
   	 */
	public void saveRtList(List<RtRgstVO> dsRtList) throws AppException;
	
	 /**
  	 * RT등록자료를 발행 한다.
  	 *
  	 * @param dsRtList RT등록자료 데이터셋
  	 * @param RT_WBL_CATE RT구분
  	 * @throws AppException
  	 */
	public List<RtRgstVO> rgstRtPrint(List<RtRgstVO> dsRtList, String RT_WBL_CATE) throws AppException;
	
	/**
  	 * RT채번정보 조회한다.
  	 *
  	 * @param HashMap<String, Object> param
  	 * @throws AppException
  	 */
	List<Map<String, Object>> selectInitSeqList(HashMap<String, Object> param) throws AppException;
}

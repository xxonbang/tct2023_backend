package com.shl.app.cus.rtf.mapper;

import java.util.List;
import java.util.Map;

import com.shl.app.cus.rtf.model.RtRgstVO;
import com.shl.app.utils.model.ComboCodeVO;

/**
 * <pre>
 * RT송장등록 매퍼
 * <pre>
 * 
 * @author eungsik
 * @since 2019.04.18
 * @version 1.0
 * @see
 * ================== 변경 내역 ==================
 * 날짜			변경자		내용
 * -----------------------------------------------
 * 2019.04.18	eungsik		최초작성
 */
public interface RtRgstMapper {
	
	/**
   	 * 해당 브랜드의 대표고객코드를 조회한다.
   	 *
   	 * @param 브랜드번호
   	 * @return 대표고객코드
   	 */
	public String selectRepzntCstrCd(String BRND_NUM);
	
	/**
   	 * 브랜드번호를 조회한다.
   	 *
   	 * @param 브랜드번호
   	 * @return 브랜드번호 목록
   	 */
	public List<ComboCodeVO> selectBrnd(String BRND_NUM);
	
	/**
   	 * AS매장 조회한다.
   	 *
   	 * @param 브랜드번호
   	 * @return 매장 목록
   	 */
	public List<ComboCodeVO> selectAsOrgList(String BRND_NUM);
	
	/**
   	 * 대표고객코드로 브랜드번호 목록을 조회한다.
   	 *
   	 * @param 대표고객코드
   	 * @return 브랜드번호 목록
   	 */
	public List<ComboCodeVO> selectBrndList(String REPZNT_CSTR_CD);
	
    /**
   	 * RT등록자료 목록을 조회한다.
   	 *
   	 * @param args
   	 * @return RT등록자료 목록
   	 */
	public List<RtRgstVO> selectRtList(Map<String, String> args);
	
	/**
   	 * RT Report를 조회한다.
   	 *
   	 * @param args
   	 * @return RT등록자료 목록
   	 */
	public List<RtRgstVO> selectReport(Map<String, Object> args);
	
	/**
   	 * 해당 고객의 RT번호 발본 구조를 조회한다.
   	 *
   	 * @param args
   	 * @return SEQ_NM object name
   	 */
	public String selectExistRtNumStrBs(Map<String, String> args);
	
	/**
   	 * 해당 고객의 발본 구조로 RT번호를 생성한다.
   	 *
   	 * @param args
   	 * @return RT_NUM RT번호
   	 */
	public String selectStrBsRtNum(Map<String, String> args);
	
	/**
   	 * RT번호 시퀀스 object를 조회한다.
   	 *
   	 * @param PU_BRND_NUM 고객번호
   	 * @return NAME object name
   	 */
	public String selectExistObject(String PU_BRND_NUM);
	
	/**
   	 * RT번호 시퀀스 이름을 생성한다.
   	 *
   	 * @param PU_BRND_NUM 고객번호
   	 * @return SEQ_NM sequence name
   	 */
	public String selectRtNumSeqNm(String PU_BRND_NUM);
	
	/**
   	 * RT번호 시퀀스를 생성한다.
   	 *
   	 * @param SEQ_NM sequence name
   	 */
	public void createRtNumSeq(Map<String, String> args);
	
	/**
   	 * RT번호를 생성한다.
   	 *
   	 * @param PU_BRND_NUM 고객번호
   	 * @param SEQ_NM sequence name
   	 * @return RT_NUM RT번호
   	 */
	public String selectRtNum(Map<String, String> args);
	
	/**
   	 * RT번호 중복 체크를 조회한다.
   	 *
   	 * @param RT_NUM RT번호
   	 * @return RT_NUM RT번호
   	 */
	public String selectExistRtNum(String RT_NUM);
	
	/**
   	 * 발행키 체크를 조회한다.
   	 *
   	 * @param RT_NUM RT번호
   	 * @return RGST_RT_KEY_NUM 발행키번호
   	 */
	public String selectExistRgstRtKeyNum(String RT_NUM);
	
	/**
   	 * 발행키를 생성한다.
   	 *
   	 * @return RGST_RT_KEY_NUM 발행키번호
   	 */
	public String selectRgstRtKeyNum();
	
	/**
   	 * RT 데이터를 등록 한다.
   	 *
   	 * @param RtRgstVO
   	 */
	public void insertRt(RtRgstVO rtWblRgstVO);
	
	/**
   	 * RT 데이터를 수정 한다.
   	 *
   	 * @param RtRgstVO
   	 */
	public void updateRt(RtRgstVO rtWblRgstVO);
	

	/**
   	 * RT 데이터를 삭제 한다. (삭제여부를 'Y'로 update)
   	 *
   	 * @param RtRgstVO
   	 */
	public void deleteRt(RtRgstVO rtWblRgstVO);
	
	/**
   	 * 해당 고객의 RT번호 발본 구조 정보를 조회한다.
   	 *
   	 * @param args
   	 * @return 해당 고객의 RT번호 발본 구조 정보
   	 */
	public List<RtRgstVO> selectRtNumStrBsInfo(Map<String, String> args);
	
	/**
   	 * object명을 조회한다.
   	 *
   	 * @param objNm object명
   	 * @return object명
   	 */
	public String selectObjectNm(String objNm);

	/**
   	 * 시퀀스 object를 생성한다.
   	 *
   	 * @param args
   	 */
	public void createSeqObject(Map<String, String> args);

	/**
   	 * 신규 RT번호를 생성한다.
   	 *
   	 * @param args
   	 * @return 신규 RT번호
   	 */
	public String selectNewRtNum(Map<String, String> args);
}

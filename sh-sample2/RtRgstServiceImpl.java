package com.shl.app.cus.rtf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.nexacro.uiadapter17.spring.core.data.DataSetRowTypeAccessor;
import com.nexacro17.xapi.data.DataSet;
import com.shl.app.com.sys.service.EvntLogService;
import com.shl.app.cus.rtf.mapper.RtRgstMapper;
import com.shl.app.cus.rtf.model.RtRgstVO;
import com.shl.app.cus.rtf.service.RtRgstService;
import com.shl.app.utils.func.SessionUtil;
import com.shl.app.utils.model.ComboCodeVO;
import com.shl.app.wbl.wbl.mapper.RtWblRgstPMapper;
import com.shl.app.wbl.wbl.model.RtWblRgstVO;
import com.shl.common.exception.AppException;

/**
 * RT송장등록 서비스 구현
 *
 * @author eungsik
 * @since 2019.04.18
 * @version 1.0
 * @see
 * <pre>
 * ================== 변경 내역 ==================
 * 날짜			변경자		내용
 * -----------------------------------------------
 * 2019.04.18	eungsik		최초작성
 * </pre>
 */
@Service
public class RtRgstServiceImpl implements RtRgstService {
	
	@Autowired
    SqlSessionTemplate sqlSession;
	
	@Autowired
	SessionUtil sessionUtil;
	
	@Resource(name="messageSource")
	MessageSource messageSource;
	
	@Autowired(required=true)
	EvntLogService evntLogService;
	
	/**
	 * 입력한 문자열의 값 존재여부 확인
	 * @param str String 확인하기 위한 문자열
	 * @return boolean true-값존재, false-값미존재(null)
	 */
	private boolean isNotNull(String str) {
		if(str == null) return false;
		if(str.trim().length() == 0) return false;
		
		return true;
	}
	
	/**
   	 * 같은 대표고객코드를 가진 브랜드번호 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	브랜드번호 목록
   	 */
	public List<ComboCodeVO> selectBrndList(String BRND_NUM) {
		
		RtRgstMapper mapper = sqlSession.getMapper(RtRgstMapper.class);
		
		//대표고객코드
		String REPZNT_CSTR_CD = mapper.selectRepzntCstrCd(BRND_NUM);
		
		if(isNotNull(REPZNT_CSTR_CD)) {
			return mapper.selectBrndList(REPZNT_CSTR_CD);
		} else {
			return mapper.selectBrnd(BRND_NUM);
		}
	}
	
	/**
   	 * 같은 대표고객코드를 가진 브랜드번호 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	대표고객
   	 */
	public String selectRepzntCstr(String BRND_NUM) {
		
		RtRgstMapper mapper = sqlSession.getMapper(RtRgstMapper.class);
		
		//대표고객코드
		return mapper.selectRepzntCstrCd(BRND_NUM);
	}
	
	/**
   	 * AS가능 매장 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	매장 목록
   	 */
	public List<ComboCodeVO> selectAsOrgList(String BRND_NUM) {
		
		RtRgstMapper mapper = sqlSession.getMapper(RtRgstMapper.class);
		
		return mapper.selectAsOrgList(BRND_NUM);
	}
	
    /**
   	 * RT등록자료 목록을 조회한다.
   	 * 
   	 * @param PU_BRND_NUM 고객번호
   	 * @param PU_BRND_ORG_NUM 발신지점
   	 * @param OPRT_DT 등록일자
   	 * @param RT_NUM
   	 * @return	RT등록자료 목록
   	 */
	@Override
	public List<RtRgstVO> selectRtList(String PU_BRND_NUM, String PU_BRND_ORG_NUM, String OPRT_DT, String RT_NUM) {
		
		RtRgstMapper mapper = sqlSession.getMapper(RtRgstMapper.class);
		
		Map<String, String> args = new HashMap<>();
		args.put("PU_BRND_NUM", PU_BRND_NUM);
		args.put("PU_BRND_ORG_NUM", PU_BRND_ORG_NUM);
		args.put("OPRT_DT", OPRT_DT);
		args.put("RT_NUM", RT_NUM);
		args.put("SES_EMP_NUM", sessionUtil.getEmpNum());
		
        return mapper.selectRtList(args);
	}
	
	
	
	/**
   	 * RT등록자료를 저장 한다.
   	 *
   	 * @param dsRtList RT등록자료 데이터셋
   	 * @throws AppException
   	 */
	public void saveRtList(List<RtRgstVO> dsRtList) throws AppException {
		
		RtRgstMapper mapper = sqlSession.getMapper(RtRgstMapper.class);
		
		for ( int i=0; i < dsRtList.size(); i++ ) {
			RtRgstVO rtWblRgstVO = dsRtList.get(i);
			
			if (rtWblRgstVO instanceof DataSetRowTypeAccessor){
                DataSetRowTypeAccessor accessor =  rtWblRgstVO;
                
                if (accessor.getRowType() == DataSet.ROW_TYPE_INSERTED){
                	
                	rtInsertTyp1(mapper, rtWblRgstVO);
                	
                }else if (accessor.getRowType() == DataSet.ROW_TYPE_UPDATED){
                	
                	//update
                	if("Y".equals(rtWblRgstVO.getRPR_YN())) {
	                	String AS_BRND_ORG_NUM = rtWblRgstVO.getAS_BRND_ORG_NUM();
                		rtWblRgstVO.setDLVR_BRND_ORG_NUM(AS_BRND_ORG_NUM);
                	} 
                	
                	rtWblRgstVO.setCORCT_EMP_NUM(sessionUtil.getEmpNum());
                	mapper.updateRt(rtWblRgstVO);
                	
                }else if (accessor.getRowType() == DataSet.ROW_TYPE_DELETED){
                	
                	//발행여부 체크
                	String strRgstRtKeyNum = mapper.selectExistRgstRtKeyNum(rtWblRgstVO.getRT_NUM());
                	
                	if(isNotNull(strRgstRtKeyNum)) {
        				throw new AppException(messageSource.getMessage("S00013", new String[]{"발행된 자료는 삭제할 수 없습니다."}, Locale.getDefault()));
        			}
                	
                	//delete (삭제여부를 'Y'로 update)
                	rtWblRgstVO.setCORCT_EMP_NUM(sessionUtil.getEmpNum());
                	
                	evntLogService.saveEvntLog("의류관리 TYPE1 삭제", "deleteRt"); // 삭제 로그 저장
                	mapper.deleteRt(rtWblRgstVO);
                }
            }
		}//end of for
	}

	private void rtInsertTyp1(RtRgstMapper mapper, RtRgstVO rtWblRgstVO) throws AppException {
		
		// RT자동생성여부
    	if ( "Y".equalsIgnoreCase(rtWblRgstVO.getRT_AUTO()) ) {
    		//RT자동생성여부
    		String strSeqNm = ""; // 시퀀스명 
    		String strRtNum = ""; // RT번호 
    		
    		// 해당 고객의 정해진 RT번호 발본 구조 정보 조회
    		Map<String, String> argsRt = new HashMap<>();
    		argsRt.put("PU_BRND_NUM", rtWblRgstVO.getPU_BRND_NUM());
    		

    		// 수선인 경우에도 자동채번되도록 요청(권수연2020-08-25)
        	//argsRt.put("RPR_YN", "N");
        	argsRt.put("RPR_YN", rtWblRgstVO.getRPR_YN());
        	
    		List<RtRgstVO> rtNumStrBsInfo = mapper.selectRtNumStrBsInfo(argsRt);
    		
    		
    		if ( !rtNumStrBsInfo.isEmpty() ) {
    			
    			// 조회된 RT번호 발본 구조 정보
    			String brndNum = rtNumStrBsInfo.get(0).getBRND_NUM();
    			String seqNm = rtNumStrBsInfo.get(0).getSEQ_NM();
    			String frmChr = rtNumStrBsInfo.get(0).getFRM_CHR();
    			String maxvalue = rtNumStrBsInfo.get(0).getMAXVALUE();
    			
    			// 시퀀스명 만들기
    			if ( "CSTR_ORG_NUM".equals(seqNm) ) {
    				
    				// ex) SEQ_RT_ORG_0000000
    				strSeqNm = "SEQ_RT_ORG_" + rtWblRgstVO.getPU_BRND_ORG_NUM();
    				
    			} else if ( "BRND".equals(seqNm) ) {
    				
    				// ex) SEQ_RT_BRND_0000
    				strSeqNm = "SEQ_RT_BRND_" + rtWblRgstVO.getPU_BRND_NUM();
    				
    			} else if ( "BRNDC".equals(seqNm) ) {
    				
    				// ex) SEQ_RT_BRNDC_0000
    				strSeqNm = "SEQ_RT_BRNDC_" + brndNum;
    				
    			} else {
    				
    				throw new AppException(messageSource.getMessage("S00013",
    						new String[]{"발행되는 RT번호 룰이 맞지 않습니다."}, Locale.getDefault()));
    				
    			}
    			
    			// 시퀀스 존재 여부 확인
    			String orgSeqNm = mapper.selectObjectNm(strSeqNm);
    			
    			// 시퀀스가 없는 경우
    			if ( isNotNull(orgSeqNm) == false ) {
    				
    				// 시퀀스 object 생성
    				Map<String, String> args = new HashMap<>();
    				args.put("SEQ_NM", strSeqNm);
    				args.put("FRM_CHR", frmChr);
    				args.put("MAXVALUE", maxvalue);
    				mapper.createSeqObject(args);
    				
    			}
    			
    			// 해당 고객의 발본 구조로 RT번호 생성
    			Map<String, String> args = new HashMap<>();
    			args.put("PU_BRND_ORG_NUM", rtWblRgstVO.getPU_BRND_ORG_NUM());
    			args.put("SEQ_NM", strSeqNm);
    			args.put("PU_BRND_NUM", rtWblRgstVO.getPU_BRND_NUM());
    			
    			args.put("RPR_YN", rtWblRgstVO.getRPR_YN());
    			// 수선인 경우에도 자동채번되도록 요청(권수연2020-08-25)
            	//args.put("RPR_YN", "N");
    			
    			strRtNum = mapper.selectNewRtNum(args);
    			
    		} 
    		// 발본구조 없으면
    		else {
    			
    			//RT번호 시퀀스 object 조회
    			strSeqNm = mapper.selectExistObject(rtWblRgstVO.getPU_BRND_NUM());
    			if(isNotNull(strSeqNm) == false) {
    				
    				//없으면 RT번호 시퀀스 생성
    				strSeqNm = mapper.selectRtNumSeqNm(rtWblRgstVO.getPU_BRND_NUM());
    				Map<String, String> args = new HashMap<>();
    				args.put("SEQ_NM", strSeqNm);
    				mapper.createRtNumSeq(args);
    			}
    			
    			//RT번호 생성
    			Map<String, String> args = new HashMap<>();
    			args.put("PU_BRND_NUM", rtWblRgstVO.getPU_BRND_NUM());
    			args.put("SEQ_NM", strSeqNm);
    			strRtNum = mapper.selectRtNum(args);
    		}
    		
    		// 있으면
    		rtWblRgstVO.setRT_NUM(strRtNum);
    	} else {
    		// RT번호 중복 체크
        	checkExistRtNum(mapper, rtWblRgstVO.getRT_NUM());
    	}
		
		rtWblRgstVO.setPRNT_YN("N");	//발행여부(N)
		rtWblRgstVO.setRGST_EMP_NUM(sessionUtil.getEmpNum());
		
		// 수선일 경우 수선콤보 데이터를 수신부서에 설정
		if("Y".equals(rtWblRgstVO.getRPR_YN())) {
			String AS_BRND_ORG_NUM = rtWblRgstVO.getAS_BRND_ORG_NUM();
			rtWblRgstVO.setDLVR_BRND_ORG_NUM(AS_BRND_ORG_NUM);
		} 
		
		rtWblRgstVO.setRGST_CATE_KND("C");
		
		//insert
		mapper.insertRt(rtWblRgstVO);
	}
	
	/**
   	 * RT등록자료를 발행 한다.
   	 *
   	 * @param dsRtList RT등록자료 데이터셋
   	 * @return RT등록자료 목록
   	 * @throws AppException
   	 */
	public List<RtRgstVO> rgstRtPrint(List<RtRgstVO> dsRtList, String RT_WBL_CATE) throws AppException {
		
		RtRgstMapper mapper = sqlSession.getMapper(RtRgstMapper.class);
		List<String> rtNumList = new ArrayList<>();
		
		for ( int i=0; i < dsRtList.size(); i++ ) {
			RtRgstVO rtWblRgstVO = dsRtList.get(i);
			
			//체크박스
			if ("1".equals(rtWblRgstVO.get_CHK())) {
				
				// RT자동생성					
        		String strSeqNm = "";
        		String strRtNum = "";
        		
        		//해당 고객의 정해진 RT번호 발본 구조가 있는지 조회
        		Map<String, String> argsRt = new HashMap<>();
        		argsRt.put("PU_BRND_NUM",	rtWblRgstVO.getPU_BRND_NUM());
        		argsRt.put("RPR_YN",		rtWblRgstVO.getRPR_YN());
        		List<RtRgstVO> rtNumStrBsInfo = mapper.selectRtNumStrBsInfo(argsRt);
        		
        		//고객 채번규칙 있으면
        		if ( rtNumStrBsInfo != null && !rtNumStrBsInfo.isEmpty() ) {
        			
        			// 조회된 RT번호 발본 구조 정보
        			String brndNum = rtNumStrBsInfo.get(0).getBRND_NUM();
        			String seqNm = rtNumStrBsInfo.get(0).getSEQ_NM();
        			String frmChr = rtNumStrBsInfo.get(0).getFRM_CHR();
        			String maxvalue = rtNumStrBsInfo.get(0).getMAXVALUE();

        			// 시퀀스명 만들기
        			if ( "CSTR_ORG_NUM".equals(seqNm) ) {

        				// ex) SEQ_RT_ORG_0000000
        				strSeqNm = "SEQ_RT_ORG_" + rtWblRgstVO.getPU_BRND_ORG_NUM();

        			} else if ( "BRND".equals(seqNm) ) {

        				// ex) SEQ_RT_BRND_0000
        				strSeqNm = "SEQ_RT_BRND_" + rtWblRgstVO.getPU_BRND_NUM();

        			} else if ( "BRNDC".equals(seqNm) ) {

        				// ex) SEQ_RT_BRNDC_0000
        				strSeqNm = "SEQ_RT_BRNDC_" + brndNum;

        			} else {

        				throw new AppException(messageSource.getMessage("S00013",
        						new String[]{"발행되는 RT번호 룰이 맞지 않습니다."}, Locale.getDefault()));

        			}

        			// 시퀀스 존재 여부 확인
        			String orgSeqNm = mapper.selectObjectNm(strSeqNm);

        			// 시퀀스가 없는 경우
        			if ( isNotNull(orgSeqNm) == false ) {

        				// 시퀀스 object 생성
        				Map<String, String> args = new HashMap<>();
            			args.put("SEQ_NM", strSeqNm);
            			args.put("FRM_CHR", frmChr);
            			args.put("MAXVALUE", maxvalue);
            			mapper.createSeqObject(args);

        			}

        			// 해당 고객의 발본 구조로 RT번호 생성
        			Map<String, String> args = new HashMap<>();
        			args.put("PU_BRND_ORG_NUM", rtWblRgstVO.getPU_BRND_ORG_NUM());
        			args.put("SEQ_NM", strSeqNm);
            		args.put("PU_BRND_NUM", rtWblRgstVO.getPU_BRND_NUM());
            		args.put("RPR_YN", rtWblRgstVO.getRPR_YN());
            		strRtNum = mapper.selectNewRtNum(args);
            		
        		//고객 채번규칙 없으면
        		} else {
            		//RT번호 시퀀스 object 조회
            		strSeqNm = mapper.selectExistObject(rtWblRgstVO.getPU_BRND_NUM());
            		if(isNotNull(strSeqNm) == false) {
            			
            			//없으면 RT번호 시퀀스 생성
            			strSeqNm = mapper.selectRtNumSeqNm(rtWblRgstVO.getPU_BRND_NUM());
            			
            			Map<String, String> args = new HashMap<>();
            			args.put("SEQ_NM", strSeqNm);
            			mapper.createRtNumSeq(args);
            		}
            		
            		//RT번호 생성
            		Map<String, String> args = new HashMap<>();
            		args.put("PU_BRND_NUM", rtWblRgstVO.getPU_BRND_NUM());
            		args.put("SEQ_NM", strSeqNm);
            		strRtNum = mapper.selectRtNum(args);
        		}
        		
        		//발행키
        		String strRgstRtKeyNum = mapper.selectRgstRtKeyNum();
        		
        		rtWblRgstVO.setRGST_RT_KEY_NUM(strRgstRtKeyNum);	
        		rtWblRgstVO.setRT_NUM(strRtNum);
            	rtWblRgstVO.setPRNT_YN("Y");						//발행여부(Y)
            	rtWblRgstVO.setRGST_EMP_NUM(sessionUtil.getEmpNum());
            	
            	// 수선일 경우 수선콤보 데이터를 수신부서에 설정
            	if("Y".equals(rtWblRgstVO.getRPR_YN())) {
                	String AS_BRND_ORG_NUM = rtWblRgstVO.getAS_BRND_ORG_NUM();
            		rtWblRgstVO.setDLVR_BRND_ORG_NUM(AS_BRND_ORG_NUM);
            	}
            	
            	rtWblRgstVO.setRGST_CATE_KND("C");
            	
            	//insert
            	mapper.insertRt(rtWblRgstVO);
            	//발행한 RT번호 List 생성
            	rtNumList.add(rtWblRgstVO.getRT_NUM());
			
			}//end if(chk)
			
		}//end of for
		
		Map<String, Object> args = new HashMap<>();
		args.put("RT_WBL_CATE", RT_WBL_CATE);
		args.put("RT_NUM_List", rtNumList);
		
		return mapper.selectReport(args);
	}
	
	/**
	 * 사용자가 입력한 RT번호 중복 체크
	 *
	 * @param mapper RtWblRgstPMapper
	 * @param RT_NUM RT번호
	 * @throws AppException
	 */
	private void checkExistRtNum(RtRgstMapper mapper, String RT_NUM) throws AppException {

		if(RT_NUM != null && !"".equals(RT_NUM)) {
			String rtNum = mapper.selectExistRtNum(RT_NUM);

			if(isNotNull(rtNum)) {
				throw new AppException(messageSource.getMessage("S00009", new String[]{"입력한 RT번호는", "RT번호"}, Locale.getDefault()));
			}
		}

	}

	@Override
	public List<Map<String, Object>> selectInitSeqList(HashMap<String, Object> param) throws AppException {
		// RT MASTER MAPPER
		RtWblRgstPMapper mapper = sqlSession.getMapper(RtWblRgstPMapper.class);
		
		String strSeqNm = "";
		String strRtNum = "";
		String sbrndNum = "";
		String sbrndOrgNum = "";
		
		//해당 고객의 정해진 RT번호 발본 구조가 있는지 조회
		Map<String, String> argsRt = new HashMap<>();
		sbrndNum =  param.get("SEARCH_CUST_SEQ").toString();
		sbrndOrgNum =  param.get("PU_BRND_ORG_NUM").toString();
		
		argsRt.put("PU_BRND_NUM", sbrndNum);
		argsRt.put("RPR_YN", "N");
		//strSeqNm = mapper.selectExistRtNumStrBs(argsRt);
		List<RtWblRgstVO> rtNumStrBsInfo = mapper.selectRtNumStrBsInfo(argsRt);
		
		if ( !rtNumStrBsInfo.isEmpty() ) {		
			
			// 조회된 RT번호 발본 구조 정보
			String brndNum = rtNumStrBsInfo.get(0).getBRND_NUM();
			String seqNm = rtNumStrBsInfo.get(0).getSEQ_NM();
			String frmChr = rtNumStrBsInfo.get(0).getFRM_CHR();
			String maxvalue = rtNumStrBsInfo.get(0).getMAXVALUE();
	
			// 시퀀스명 만들기
			if ( "CSTR_ORG_NUM".equals(seqNm) ) {
	
				// ex) SEQ_RT_ORG_0000000
				//strSeqNm = "SEQ_RT_ORG_" + vo.getPU_BRND_ORG_NUM();
				strSeqNm = "SEQ_RT_ORG_" + sbrndOrgNum;
	
			} else if ( "BRND".equals(seqNm) ) {
	
				// ex) SEQ_RT_BRND_0000
				strSeqNm = "SEQ_RT_BRND_" + sbrndNum;
	
			} else if ( "BRNDC".equals(seqNm) ) {
	
				// ex) SEQ_RT_BRNDC_0000
				strSeqNm = "SEQ_RT_BRNDC_" + brndNum;
	
			} else {
				throw new AppException(messageSource.getMessage("S00013",
						new String[]{"발행되는 RT번호 룰이 맞지 않습니다."}, Locale.getDefault()));
	
			}
			
			// 시퀀스 존재 여부 확인
			String orgSeqNm = mapper.selectObjectNm(strSeqNm);
			
			// 시퀀스가 없는 경우
			if ( isNotNull(orgSeqNm) == false ) {
	
				// 시퀀스 object 생성
				Map<String, String> args = new HashMap<>();
				args.put("SEQ_NM", strSeqNm);
				args.put("FRM_CHR", frmChr);
				args.put("MAXVALUE", maxvalue);
				mapper.createSeqObject(args);
	
			}
	
			// 해당 고객의 발본 구조로 RT번호 생성
			HashMap<String, String> args = new HashMap<>();
			args.put("PU_BRND_ORG_NUM", sbrndOrgNum);
			args.put("SEQ_NM", strSeqNm);
			args.put("PU_BRND_NUM", sbrndNum);
			args.put("RPR_YN", "N");
			strRtNum = mapper.selectNewRtNum(args);
			
			
		}
		// 없으면
		else {
		
			//RT번호 시퀀스 object 조회
			strSeqNm = mapper.selectExistObject(sbrndNum);
			
			if(isNotNull(strSeqNm) == false) {
				
				//없으면 RT번호 시퀀스 생성
				strSeqNm = mapper.selectRtNumSeqNm(sbrndNum);
				Map<String, String> args = new HashMap<>();
				args.put("SEQ_NM", strSeqNm);
				mapper.createRtNumSeq(args);
			}
			
			//RT번호 생성
			Map<String, String> args = new HashMap<>();
			args.put("PU_BRND_NUM", sbrndNum);
			args.put("SEQ_NM", strSeqNm);
			strRtNum = mapper.selectRtNum(args);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("DEF_SEQ", strRtNum);
		
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		dataList.add(map);
        return dataList;
	}
}

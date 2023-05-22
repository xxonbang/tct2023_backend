package com.shl.app.cus.rtf.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexacro.uiadapter17.spring.core.annotation.ParamDataSet;
import com.nexacro.uiadapter17.spring.core.annotation.ParamVariable;
import com.nexacro.uiadapter17.spring.core.data.NexacroResult;
import com.shl.app.cus.dof.service.RcvMgmtService;
import com.shl.app.cus.rtf.model.RtRgstVO;
import com.shl.app.cus.rtf.service.RtRgstService;
import com.shl.app.utils.model.ComboCodeVO;
import com.shl.app.utils.service.ComboCodeService;
import com.shl.common.exception.AppException;

/**
 * <pre>
 * RT송장등록 컨트롤러
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
 * </pre>
 */
@Controller
public class RtRgstController {
	
	private final Logger logger = LoggerFactory.getLogger(RtRgstController.class);
	
	@Resource(name="messageSource")
	MessageSource messageSource;
	
	@Autowired(required=true)
	RtRgstService rtWblRgstService;
	
	@Autowired(required=true)
    ComboCodeService comboCodeService;
	
	@Autowired(required=true)
	RcvMgmtService rcvMgmtService;
	
	/**
	 * (콤보구성용) 콤보 목록을 조회한다.
	 * @param codeString, ORG_CODE
	 * @return	콤보 목록
   	 * @throws AppException
	 */
    @RequestMapping(value = "/cus/rtf/rtrgst/code")
    public NexacroResult selectCodeList(
    			@ParamVariable(name="codes", required = false) String codeString
    		   ,@ParamVariable(name="BRND_NUM", required = false) String BRND_NUM
    		   ,@ParamVariable(name="PU_BRND_ORG_NUM", required = false) String PU_BRND_ORG_NUM
    		) throws AppException {

    	logger.debug("RtRgstController::selectCodeList called");

    	// 공통코드(패션 RT 세부구분)
    	List<ComboCodeVO> codes = comboCodeService.selectComboCodeList(codeString);
    	// AS매장
    	List<ComboCodeVO> orgList = rtWblRgstService.selectAsOrgList(BRND_NUM);
    	// 대표고객 
    	String repzntCstr = rtWblRgstService.selectRepzntCstr(BRND_NUM);
    	
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();
       	param.put("SEARCH_CUST_SEQ", BRND_NUM);
       	param.put("PU_BRND_ORG_NUM", PU_BRND_ORG_NUM);       	
       	List<Map<String, Object>> returnData1 = rtWblRgstService.selectInitSeqList(param); // 접속 고객번호의 RT SEQ 조회

   		NexacroResult result = new NexacroResult();

		result.addDataSet("dsCode", codes);
		result.addDataSet("dsAsOrg", orgList);
		result.addDataSet("dsDefSeq", returnData1);
		
		// 대표고객 반환
		result.addVariable("REPZNT_CSTR_CD", repzntCstr);
		
		return result;

	}
    
    /**
   	 * RT등록자료 목록을 조회한다.
   	 * 
   	 * @param PU_BRND_NUM 고객번호
   	 * @param PU_BRND_ORG_NUM 발신지점
   	 * @param OPRT_DT 등록일자
   	 * @return	RT등록자료 목록
   	 * @throws AppException
   	 */
    @RequestMapping(value = "/cus/rtf/rtrgst/searchRtList")
   	public NexacroResult selectRtList(
   				@ParamVariable(name="PU_BRND_NUM", required=false) String PU_BRND_NUM,
   				@ParamVariable(name="PU_BRND_ORG_NUM", required=false) String PU_BRND_ORG_NUM,
   				@ParamVariable(name="OPRT_DT", required=false) String OPRT_DT
   			) throws AppException {
    	
       	logger.debug("RtRgstController::selectRtList called");
       	
   		List<RtRgstVO> rtList = rtWblRgstService.selectRtList(PU_BRND_NUM, PU_BRND_ORG_NUM, OPRT_DT, "");
   		
   		NexacroResult result = new NexacroResult();
   		result.addDataSet("dsRtList", rtList);
   		
   		return result;
   	}
    
    
    /**
   	 * RT등록자료를 저장 한다.
   	 *
   	 * @param dsRtList RT등록자료 데이터셋
   	 * @return NexacroResult
   	 * @throws AppException
   	 */
    @RequestMapping(value = "/cus/rtf/rtrgst/saveRtList")
   	public NexacroResult saveRtList(
   				@ParamDataSet(name="dsRtList", required=false) List<RtRgstVO> dsRtList
   			) throws AppException {
    	
    	logger.debug("RtRgstController::saveRtList called");
    	
    	rtWblRgstService.saveRtList(dsRtList);
    	
   		return new NexacroResult();
   	}
    
    /**
   	 * RT등록자료를 발행 한다.
   	 *
   	 * @param dsRtList RT등록자료 데이터셋
   	 * @return NexacroResult
   	 * @throws AppException
   	 */
    @RequestMapping(value = "/cus/rtf/rtrgst/rgstRtList")
   	public NexacroResult rgstRtPrint(
   				@ParamDataSet(name="dsRtList", required=false) List<RtRgstVO> dsRtList
   				,@ParamVariable(name="RT_WBL_CATE", required=false) String RT_WBL_CATE
   			) throws AppException {
    	
    	logger.debug("RtRgstController::rgstRtList called");
    	
    	List<RtRgstVO> rtList = rtWblRgstService.rgstRtPrint(dsRtList, RT_WBL_CATE);
    	
   		NexacroResult result = new NexacroResult();
   		// C0420001:RT라벨(82X90)
   		if ( "C0420001".equals(RT_WBL_CATE) ) {
   			result.addDataSet("dsPrint01", rtList);
   		}
   		// C0420002:이랜드RT라벨(90X66)
   		else if ( "C0420002".equals(RT_WBL_CATE) ) {
   			result.addDataSet("dsPrint02", rtList);
   		}
   		// C0420003:삼성생명서비스/현대자동차 문서집중국 라벨(100X90)
   		else if ( "C0420003".equals(RT_WBL_CATE) ) {
   			result.addDataSet("dsPrint03", rtList);
   		}
   		// C0420004:AS라벨(70X50)
   		else if ( "C0420004".equals(RT_WBL_CATE) ) {
   			result.addDataSet("dsPrint04", rtList);
   		}
   		// C0420005:코웨이문서라벨(80X40)
   		else if ( "C0420005".equals(RT_WBL_CATE) ) {
   			result.addDataSet("dsPrint05", rtList);
   		}
   		
   		return result;
   	}
    
	/**
   	 * 같은 대표고객코드를 가진 브랜드번호 목록을 조회한다.
   	 * 
   	 * @param BRND_NUM 브랜드번호
   	 * @return	브랜드번호 목록
   	 * @throws AppException
   	 */
    @RequestMapping(value = "/cus/rtf/rtrgst/searchBrndList")
   	public NexacroResult selectBrndList(
   				@ParamVariable(name="BRND_NUM", required=false) String BRND_NUM) throws AppException {
    	
       	logger.debug("RtRgstController::selectBrndList called");
       	
       	//고객번호 콤보
   		List<ComboCodeVO> brndList = rtWblRgstService.selectBrndList(BRND_NUM);
   		
   		NexacroResult result = new NexacroResult();
   		result.addDataSet("dsBrnd", brndList);
   		
   		return result;
   	}
	
    
    
    
   
}

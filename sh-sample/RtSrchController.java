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

import com.nexacro.uiadapter17.spring.core.annotation.ParamVariable;
import com.nexacro.uiadapter17.spring.core.data.NexacroResult;
import com.shl.common.exception.AppException;

import com.shl.app.cus.rtf.service.RtSrchService;


/**
 * <pre>
 * @Desc RT 조회 컨트롤러
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

@Controller
public class RtSrchController {
	private Logger logger = LoggerFactory.getLogger(RtSrchController.class);
	
	@Resource(name="messageSource")
	MessageSource messageSource;
	
	@Autowired(required=true)
	RtSrchService rtSrchService;
	
	@RequestMapping(value = "/cus/rtf/rtSrch/list")
   	public NexacroResult selectRtSrchList(
   			 @ParamVariable(name="PU_BRND_NUM", required = false) String puBrndNum
   			,@ParamVariable(name="RT_NUM", required = false) String rtNum
   			,@ParamVariable(name="BRND_ORG_NUM", required = false) String BRND_ORG_NUM
   			) throws AppException {

       	logger.debug("/cus/rtf/rtSrch/list::called");

       	HashMap<String, Object> param = new HashMap<String, Object>();
       	param.put("PU_BRND_NUM", puBrndNum);
       	param.put("RT_NUM", rtNum);
       	param.put("BRND_ORG_NUM", BRND_ORG_NUM);
		
   		List<Map<String, Object>> resultDs = rtSrchService.selectRtSrchList(param);   		   		

   		NexacroResult result = new NexacroResult();
   		result.addDataSet("dsRtInfo", resultDs);   		

   		return result;
   	}
    	
}

package com.lgcns.tct.service;

import com.lgcns.tct.dto.UsedataDto;
import com.lgcns.tct.dto.UsedListDto;
import com.lgcns.tct.mapper.UsedataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UseddataServiceimpl implements UsedataService {

    @Autowired
    private final UsedataMapper usedataMapper;
    
	@Override
	public UsedataDto getUseData(String user_no, String start_dt){

        UsedataDto result = usedataMapper.getUseData(user_no, start_dt);
		return result;
	}

    
	@Override
	public Map<String, Object> getUsedList(String user_no, String start_dt){
		
        List<UsedListDto> list = usedataMapper.getUsedList(user_no, start_dt);


		Map<String, Object> result = new HashMap<>();
        result.put("list", list);

		return result;
	}
}

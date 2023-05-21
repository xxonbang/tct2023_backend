package com.lgcns.tct.mapper;

import com.lgcns.tct.dto.UsedataDto;
import com.lgcns.tct.dto.UsedListDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UsedataMapper {    
    

    UsedataDto getUseData(@Param("user_no")String user_no, @Param("start_dt")String start_dt);

    List<UsedListDto> getUsedList(@Param("user_no")String user_no, @Param("start_dt")String start_dt);

}





package com.lgcns.tct.service;

import org.apache.ibatis.annotations.Param;
import com.lgcns.tct.dto.UsedataDto;
import java.util.*;

public interface UsedataService {

    public UsedataDto getUseData(@Param("user_no")String user_no, @Param("start_dt")String start_dt);

    public Map<String, Object> getUsedList(@Param("user_no")String user_no, @Param("start_dt")String start_dt);
}

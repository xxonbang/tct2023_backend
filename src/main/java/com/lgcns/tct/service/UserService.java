package com.lgcns.tct.service;

import org.apache.ibatis.annotations.Param;
import com.lgcns.tct.dto.UserInfoDto;

public interface UserService {
    public UserInfoDto getUserInfo(@Param("user_no")String user_no);
}

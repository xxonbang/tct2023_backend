package com.lgcns.tct.mapper;
import com.lgcns.tct.dto.UserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserInfoDto getUserInfo(@Param("user_no")String user_no);
}

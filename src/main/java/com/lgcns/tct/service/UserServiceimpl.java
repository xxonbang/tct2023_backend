package com.lgcns.tct.service;

import com.lgcns.tct.dto.UserInfoDto;
import com.lgcns.tct.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    @Autowired
    private final UserMapper userMapper;
    
	@Override
	public UserInfoDto getUserInfo(String user_no){

        UserInfoDto result = userMapper.getUserInfo(user_no);
        
		return result;
	}
}

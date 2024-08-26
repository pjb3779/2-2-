package com.example.demo.service;

import com.example.demo.model.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserInfoById(int userId) {
        return userInfoMapper.selectUserInfoById(userId);
    }
}

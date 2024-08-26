package com.example.demo.mapper;

import com.example.demo.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT userName, Email, creatDate FROM UserAccount WHERE userID = #{userId}")
    UserInfo selectUserInfoById(int userId);
}

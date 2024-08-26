package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/info/{userId}")
    public UserInfo getUserInfo(@PathVariable int userId) {
        return userInfoService.getUserInfoById(userId);
    }
}
